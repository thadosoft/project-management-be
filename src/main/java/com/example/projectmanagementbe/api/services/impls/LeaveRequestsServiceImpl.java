package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.enums.LeaveStatus;
import com.example.projectmanagementbe.api.enums.LeaveType;
import com.example.projectmanagementbe.api.enums.NotificationType;
import com.example.projectmanagementbe.api.models.LeaveRequests;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.CreateLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.LeaveDecisionRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.SearchLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.UpdateLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.responses.LeaveResponse;
import com.example.projectmanagementbe.api.models.employee.Employee;
import com.example.projectmanagementbe.api.repositories.Employee.EmployeeRepository;
import com.example.projectmanagementbe.api.repositories.attandance.LeaveRequestsRepository;
import com.example.projectmanagementbe.api.services.LeaveBalanceService;
import com.example.projectmanagementbe.api.services.LeaveRequestsService;
import com.example.projectmanagementbe.api.services.NotificationService;
import com.example.projectmanagementbe.api.services.mail.MailService;
import com.example.projectmanagementbe.auth.enums.UserRole;
import com.example.projectmanagementbe.auth.models.User;
import com.example.projectmanagementbe.auth.repositories.UserRepository;
import com.example.projectmanagementbe.exception.ApiRequestException;
import com.example.projectmanagementbe.exception.ErrorCode;
import jakarta.persistence.criteria.Predicate;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class LeaveRequestsServiceImpl implements LeaveRequestsService {

  private static final Set<Double> VALID_PORTIONS = Set.of(0.25, 0.5, 1.0);
  private static final List<LeaveStatus> BLOCKING_STATUSES =
      List.of(LeaveStatus.PENDING, LeaveStatus.APPROVED);

  private final LeaveRequestsRepository leaveRequestsRepository;
  private final LeaveBalanceService leaveBalanceService;
  private final NotificationService notificationService;
  private final CurrentUserProvider currentUserProvider;
  private final EmployeeRepository employeeRepository;
  private final UserRepository userRepository;
  private final MailService mailService;

  @Override
  @Transactional
  public LeaveResponse create(CreateLeaveRequest request) {
    Employee employee = currentEmployee();
    Duration duration = validateAndComputeDuration(
        request.getStartDate(), request.getEndDate(), request.getDayPortion());
    ensureNoOverlap(employee.getId(), request.getStartDate(), request.getEndDate(), null);

    LeaveRequests entity = new LeaveRequests();
    entity.setEmployeeId(employee.getId());
    entity.setStartDate(request.getStartDate());
    entity.setEndDate(request.getEndDate());
    entity.setDayPortion(duration.portion());
    entity.setNumberOfDays(duration.days());
    entity.setLeaveType(LeaveType.fromNullable(request.getLeaveType()).name());
    entity.setReason(request.getReason());
    entity.setStatus(LeaveStatus.PENDING);
    leaveRequestsRepository.save(entity);

    notifyOfmNewRequest(entity, employee);
    return buildResponse(entity);
  }

  @Override
  @Transactional
  public LeaveResponse update(Long id, UpdateLeaveRequest request) {
    LeaveRequests entity = getById(id);
    Employee employee = currentEmployee();
    requireOwner(entity, employee);
    if (entity.getStatus() != LeaveStatus.PENDING) {
      throw new ApiRequestException(ErrorCode.LEAVE_INVALID_STATUS);
    }

    Duration duration = validateAndComputeDuration(
        request.getStartDate(), request.getEndDate(), request.getDayPortion());
    ensureNoOverlap(employee.getId(), request.getStartDate(), request.getEndDate(), entity.getId());

    entity.setStartDate(request.getStartDate());
    entity.setEndDate(request.getEndDate());
    entity.setDayPortion(duration.portion());
    entity.setNumberOfDays(duration.days());
    entity.setLeaveType(LeaveType.fromNullable(request.getLeaveType()).name());
    entity.setReason(request.getReason());
    leaveRequestsRepository.save(entity);
    return buildResponse(entity);
  }

  @Override
  @Transactional
  public void cancel(Long id) {
    LeaveRequests entity = getById(id);
    Employee employee = currentEmployee();
    requireOwner(entity, employee);

    switch (entity.getStatus()) {
      case PENDING -> entity.setStatus(LeaveStatus.CANCELLED);
      case APPROVED -> {
        entity.setStatus(LeaveStatus.CANCELLED);
        if (LeaveType.fromNullable(entity.getLeaveType()).isDeductsBalance()) {
          leaveBalanceService.revertLeave(
              entity.getEmployeeId(), entity.getStartDate().getYear(), entity.getNumberOfDays());
        }
      }
      default -> throw new ApiRequestException(ErrorCode.LEAVE_INVALID_STATUS);
    }
    leaveRequestsRepository.save(entity);
  }

  @Override
  @Transactional
  public LeaveResponse approve(Long id, LeaveDecisionRequest request) {
    LeaveRequests entity = getById(id);
    if (entity.getStatus() != LeaveStatus.PENDING) {
      throw new ApiRequestException(ErrorCode.LEAVE_INVALID_STATUS);
    }
    User approver = currentUserProvider.getCurrentUser();

    if (LeaveType.fromNullable(entity.getLeaveType()).isDeductsBalance()) {
      leaveBalanceService.applyLeave(
          entity.getEmployeeId(), entity.getStartDate().getYear(), entity.getNumberOfDays());
    }
    entity.setStatus(LeaveStatus.APPROVED);
    entity.setApproverId(approver.getId());
    entity.setApprovedAt(LocalDateTime.now());
    entity.setDecisionNote(request == null ? null : request.getDecisionNote());
    leaveRequestsRepository.save(entity);

    notifyRequesterDecision(entity, NotificationType.LEAVE_APPROVED,
        "Đơn xin nghỉ phép đã được duyệt");
    return buildResponse(entity);
  }

  @Override
  @Transactional
  public LeaveResponse reject(Long id, LeaveDecisionRequest request) {
    LeaveRequests entity = getById(id);
    if (entity.getStatus() != LeaveStatus.PENDING) {
      throw new ApiRequestException(ErrorCode.LEAVE_INVALID_STATUS);
    }
    if (request == null || request.getDecisionNote() == null || request.getDecisionNote().isBlank()) {
      throw new ApiRequestException(ErrorCode.LEAVE_DECISION_NOTE_REQUIRED);
    }
    User approver = currentUserProvider.getCurrentUser();
    entity.setStatus(LeaveStatus.REJECTED);
    entity.setApproverId(approver.getId());
    entity.setApprovedAt(LocalDateTime.now());
    entity.setDecisionNote(request.getDecisionNote());
    leaveRequestsRepository.save(entity);

    notifyRequesterDecision(entity, NotificationType.LEAVE_REJECTED,
        "Đơn xin nghỉ phép bị từ chối");
    return buildResponse(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public LeaveResponse findById(Long id) {
    LeaveRequests entity = getById(id);
    User user = currentUserProvider.getCurrentUser();
    if (user.getRole() != UserRole.OFM && user.getRole() != UserRole.ADMIN) {
      Employee employee = employeeRepository.findByUserId(user.getId())
          .orElseThrow(() -> new ApiRequestException(ErrorCode.EMPLOYEE_NOT_LINKED_TO_USER));
      requireOwner(entity, employee);
    }
    return buildResponse(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<LeaveResponse> getMine(Pageable pageable) {
    Employee employee = currentEmployee();
    return leaveRequestsRepository
        .findByEmployeeIdOrderByCreatedAtDesc(employee.getId(), pageable)
        .map(this::buildResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<LeaveResponse> search(SearchLeaveRequest request, Pageable pageable) {
    LeaveStatus status = parseStatus(request.getStatus());
    LocalDate start = parseDate(request.getStartDate());
    LocalDate end = parseDate(request.getEndDate());
    Long employeeId = request.getEmployeeId();

    Specification<LeaveRequests> spec = (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (status != null) {
        predicates.add(cb.equal(root.get("status"), status));
      }
      if (employeeId != null) {
        predicates.add(cb.equal(root.get("employeeId"), employeeId));
      }
      if (start != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.get("endDate"), start));
      }
      if (end != null) {
        predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), end));
      }
      if (query != null) {
        query.orderBy(cb.desc(root.get("createdAt")));
      }
      return cb.and(predicates.toArray(new Predicate[0]));
    };

    return leaveRequestsRepository.findAll(spec, pageable).map(this::buildResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<LeaveResponse> pending(Pageable pageable) {
    return leaveRequestsRepository
        .findByStatusOrderByCreatedAtDesc(LeaveStatus.PENDING, pageable)
        .map(this::buildResponse);
  }

  // ─── Notifications ────────────────────────────────────────────────

  private void notifyOfmNewRequest(LeaveRequests entity, Employee employee) {
    List<User> ofms = userRepository.findByRole(UserRole.OFM);
    if (ofms.isEmpty()) {
      log.warn("No user with role OFM found - leave request {} has no approver to notify", entity.getId());
    }
    String title = "Đơn xin nghỉ phép mới";
    String message = String.format("%s xin nghỉ %s ngày (%s - %s)",
        employee.getFullName(), entity.getNumberOfDays(), entity.getStartDate(), entity.getEndDate());
    for (User ofm : ofms) {
      notificationService.notifyUser(
          ofm.getId(), NotificationType.LEAVE_SUBMITTED, title, message, entity.getId());
    }
    List<String> emails = ofms.stream()
        .map(User::getEmail)
        .filter(Objects::nonNull)
        .toList();
    double remaining = leaveBalanceService.getRemaining(
        entity.getEmployeeId(), entity.getStartDate().getYear());
    mailService.sendLeaveRequestNotification(emails, employee.getFullName(),
        entity.getStartDate(), entity.getEndDate(), entity.getNumberOfDays(), entity.getReason(),
        remaining);
  }

  private void notifyRequesterDecision(LeaveRequests entity, NotificationType type, String title) {
    employeeRepository.findById(entity.getEmployeeId()).ifPresent(employee -> {
      if (employee.getUserId() == null) {
        return;
      }
      String message = String.format("Đơn nghỉ %s - %s%s",
          entity.getStartDate(), entity.getEndDate(),
          entity.getDecisionNote() == null ? "" : " | Ghi chú: " + entity.getDecisionNote());
      notificationService.notifyUser(employee.getUserId(), type, title, message, entity.getId());
    });
  }

  // ─── Helpers ─────────────────────────────────────────────────────

  private LeaveRequests getById(Long id) {
    return leaveRequestsRepository.findById(id)
        .orElseThrow(() -> new ApiRequestException(ErrorCode.LEAVE_REQUEST_NOT_FOUND));
  }

  private Employee currentEmployee() {
    User user = currentUserProvider.getCurrentUser();
    return employeeRepository.findByUserId(user.getId())
        .orElseThrow(() -> new ApiRequestException(ErrorCode.EMPLOYEE_NOT_LINKED_TO_USER));
  }

  private void requireOwner(LeaveRequests entity, Employee employee) {
    if (!entity.getEmployeeId().equals(employee.getId())) {
      throw new ApiRequestException(ErrorCode.UNAUTHORIZED);
    }
  }

  private void ensureNoOverlap(Long employeeId, LocalDate start, LocalDate end, Long excludeId) {
    long overlaps = excludeId == null
        ? leaveRequestsRepository.countOverlapping(employeeId, start, end, BLOCKING_STATUSES)
        : leaveRequestsRepository.countOverlappingExcludingId(
            employeeId, start, end, BLOCKING_STATUSES, excludeId);
    if (overlaps > 0) {
      throw new ApiRequestException(ErrorCode.LEAVE_DATE_OVERLAP);
    }
  }

  private Duration validateAndComputeDuration(LocalDate start, LocalDate end, Double portionInput) {
    if (start == null || end == null || start.isAfter(end)) {
      throw new ApiRequestException(ErrorCode.LEAVE_INVALID_DATE);
    }
    if (start.isBefore(LocalDate.now())) {
      throw new ApiRequestException(ErrorCode.LEAVE_INVALID_DATE);
    }
    if (start.getYear() != end.getYear()) {
      throw new ApiRequestException(ErrorCode.LEAVE_INVALID_DATE);
    }

    if (start.isEqual(end)) {
      double portion = portionInput == null ? 1.0 : portionInput;
      if (!VALID_PORTIONS.contains(portion)) {
        throw new ApiRequestException(ErrorCode.LEAVE_INVALID_DATE);
      }
      return new Duration(portion, portion);
    }

    if (portionInput != null && portionInput != 1.0) {
      throw new ApiRequestException(ErrorCode.LEAVE_INVALID_DATE);
    }
    long workingDays = workingDays(start, end);
    if (workingDays <= 0) {
      throw new ApiRequestException(ErrorCode.LEAVE_INVALID_DATE);
    }
    return new Duration(1.0, workingDays);
  }

  private static long workingDays(LocalDate start, LocalDate end) {
    long count = 0;
    for (LocalDate day = start; !day.isAfter(end); day = day.plusDays(1)) {
      DayOfWeek dow = day.getDayOfWeek();
      if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY) {
        count++;
      }
    }
    return count;
  }

  private static LeaveStatus parseStatus(String raw) {
    if (raw == null || raw.isBlank()) {
      return null;
    }
    try {
      return LeaveStatus.valueOf(raw.trim().toUpperCase());
    } catch (IllegalArgumentException ex) {
      throw new ApiRequestException(ErrorCode.LEAVE_INVALID_STATUS);
    }
  }

  private static LocalDate parseDate(String raw) {
    if (raw == null || raw.isBlank()) {
      return null;
    }
    try {
      return LocalDate.parse(raw.trim());
    } catch (DateTimeParseException ex) {
      throw new ApiRequestException(ErrorCode.LEAVE_INVALID_DATE);
    }
  }

  private LeaveResponse buildResponse(LeaveRequests entity) {
    LeaveResponse response = new LeaveResponse();
    response.setId(entity.getId());
    response.setEmployeeId(entity.getEmployeeId());
    employeeRepository.findById(entity.getEmployeeId())
        .ifPresent(employee -> response.setEmployeeName(employee.getFullName()));
    response.setStartDate(entity.getStartDate());
    response.setEndDate(entity.getEndDate());
    response.setDayPortion(entity.getDayPortion());
    response.setNumberOfDays(entity.getNumberOfDays());
    response.setLeaveType(entity.getLeaveType());
    response.setReason(entity.getReason());
    response.setStatus(entity.getStatus() == null ? null : entity.getStatus().name());
    response.setApproverId(entity.getApproverId());
    if (entity.getApproverId() != null) {
      userRepository.findById(entity.getApproverId())
          .ifPresent(user -> response.setApproverName(user.getName()));
    }
    response.setApprovedAt(entity.getApprovedAt());
    response.setDecisionNote(entity.getDecisionNote());
    if (entity.getStartDate() != null) {
      response.setRemainingLeave(leaveBalanceService
          .getRemaining(entity.getEmployeeId(), entity.getStartDate().getYear()));
    }
    response.setCreatedAt(entity.getCreatedAt());
    return response;
  }

  private record Duration(double portion, double days) {
  }
}
