package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.models.LeaveBalance;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveBalance.UpdateLeaveBalanceRequest;
import com.example.projectmanagementbe.api.models.dto.responses.LeaveBalanceResponse;
import com.example.projectmanagementbe.api.models.employee.Employee;
import com.example.projectmanagementbe.api.repositories.Employee.EmployeeRepository;
import com.example.projectmanagementbe.api.repositories.attandance.LeaveBalanceRepository;
import com.example.projectmanagementbe.api.services.LeaveBalanceService;
import com.example.projectmanagementbe.auth.models.User;
import com.example.projectmanagementbe.exception.ApiRequestException;
import com.example.projectmanagementbe.exception.ErrorCode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

  /** How far the yearly balance is allowed to go negative. */
  private static final double MAX_OVERDRAW = 3.0;
  private static final double DEFAULT_ENTITLED = 12.0;
  private static final double EPS = 1e-9;

  private final LeaveBalanceRepository leaveBalanceRepository;
  private final EmployeeRepository employeeRepository;
  private final CurrentUserProvider currentUserProvider;

  @Override
  @Transactional
  public LeaveBalance getOrCreate(Long employeeId, int year) {
    return leaveBalanceRepository.findByEmployeeIdAndYear(employeeId, year)
        .orElseGet(() -> {
          LeaveBalance balance = new LeaveBalance();
          balance.setEmployeeId(employeeId);
          balance.setYear(year);
          balance.setEntitled(DEFAULT_ENTITLED);
          balance.setUsed(0.0);
          return leaveBalanceRepository.save(balance);
        });
  }

  @Override
  @Transactional(readOnly = true)
  public double getRemaining(Long employeeId, int year) {
    return leaveBalanceRepository.findByEmployeeIdAndYear(employeeId, year)
        .map(LeaveBalance::getRemaining)
        .orElse(DEFAULT_ENTITLED);
  }

  @Override
  @Transactional
  public LeaveBalanceResponse getMyBalance(Integer year) {
    Employee employee = currentEmployee();
    int resolvedYear = year != null ? year : LocalDate.now().getYear();
    return toResponse(getOrCreate(employee.getId(), resolvedYear), employee.getFullName());
  }

  @Override
  @Transactional
  public List<LeaveBalanceResponse> list(Integer year, Long employeeId) {
    int resolvedYear = year != null ? year : LocalDate.now().getYear();

    if (employeeId != null) {
      Employee employee = employeeRepository.findById(employeeId)
          .orElseThrow(() -> new ApiRequestException(ErrorCode.EMPLOYEE_NOT_FOUND));
      return List.of(toResponse(getOrCreate(employeeId, resolvedYear), employee.getFullName()));
    }

    List<LeaveBalance> balances = leaveBalanceRepository.findByYear(resolvedYear);
    Map<Long, String> names = employeeNames(balances.stream().map(LeaveBalance::getEmployeeId).toList());
    return balances.stream()
        .map(b -> toResponse(b, names.get(b.getEmployeeId())))
        .toList();
  }

  @Override
  @Transactional
  public LeaveBalanceResponse update(Long employeeId, Integer year, UpdateLeaveBalanceRequest request) {
    Employee employee = employeeRepository.findById(employeeId)
        .orElseThrow(() -> new ApiRequestException(ErrorCode.EMPLOYEE_NOT_FOUND));
    int resolvedYear = year != null ? year : LocalDate.now().getYear();

    LeaveBalance balance = getOrCreate(employeeId, resolvedYear);
    if (request.getEntitled() != null) {
      balance.setEntitled(request.getEntitled());
    }
    if (request.getUsed() != null) {
      balance.setUsed(request.getUsed());
    }
    leaveBalanceRepository.save(balance);
    return toResponse(balance, employee.getFullName());
  }

  @Override
  @Transactional
  public void applyLeave(Long employeeId, int year, double days) {
    LeaveBalance balance = getOrCreate(employeeId, year);
    if (balance.getRemaining() - days < -MAX_OVERDRAW - EPS) {
      throw new ApiRequestException(ErrorCode.LEAVE_BALANCE_EXCEEDED);
    }
    balance.setUsed(balance.getUsed() + days);
    leaveBalanceRepository.save(balance);
  }

  @Override
  @Transactional
  public void revertLeave(Long employeeId, int year, double days) {
    LeaveBalance balance = getOrCreate(employeeId, year);
    double restored = balance.getUsed() - days;
    balance.setUsed(Math.max(0.0, restored));
    leaveBalanceRepository.save(balance);
  }

  private Employee currentEmployee() {
    User user = currentUserProvider.getCurrentUser();
    return employeeRepository.findByUserId(user.getId())
        .orElseThrow(() -> new ApiRequestException(ErrorCode.EMPLOYEE_NOT_LINKED_TO_USER));
  }

  private Map<Long, String> employeeNames(List<Long> ids) {
    if (ids.isEmpty()) {
      return Map.of();
    }
    return employeeRepository.findAllById(ids).stream()
        .collect(Collectors.toMap(Employee::getId, Employee::getFullName, (a, b) -> a));
  }

  private LeaveBalanceResponse toResponse(LeaveBalance balance, String employeeName) {
    LeaveBalanceResponse response = new LeaveBalanceResponse();
    response.setEmployeeId(balance.getEmployeeId());
    response.setEmployeeName(employeeName);
    response.setYear(balance.getYear());
    response.setEntitled(balance.getEntitled());
    response.setUsed(balance.getUsed());
    response.setRemaining(balance.getRemaining());
    return response;
  }
}
