package com.example.projectmanagementbe.api.services.impls;


import com.example.projectmanagementbe.api.mappers.AuditLogMapper;
import com.example.projectmanagementbe.api.models.AuditLog.requests.log.AuditTrailRequest;
import com.example.projectmanagementbe.api.models.AuditLog.responses.log.AuditTrailResponse;
import com.example.projectmanagementbe.api.models.AuditTrail;
import com.example.projectmanagementbe.api.repositories.AuditLog.AuditLogRepository;
import com.example.projectmanagementbe.api.services.AuditLogService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

  private final AuditLogRepository auditLogRepository;
  private final AuditLogMapper auditLogMapper;

  @Override
  public Page<AuditTrailResponse> findByParams(AuditTrailRequest auditTrailRequest, Pageable pageable) {
    Page<AuditTrail> productsList = auditLogRepository.getAllByParams(
        auditTrailRequest.getResource(),
        auditTrailRequest.getStartDate(),
        auditTrailRequest.getEndDate(), pageable);

    Pageable sortedPageable = PageRequest.of(
        pageable.getPageNumber(),
        pageable.getPageSize(),
        Sort.by(Sort.Direction.DESC, "createdDate"));

    List<AuditTrailResponse> auditTrailResponses = productsList.stream()
        .map(auditLogMapper::mapAuditTrailResponses)
        .collect(Collectors.toList());
    return new PageImpl<>(auditTrailResponses, sortedPageable, productsList.getTotalElements());
  }
}
