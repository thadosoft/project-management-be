package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.AuditLog.requests.log.AuditTrailRequest;
import com.example.projectmanagementbe.api.models.AuditLog.responses.log.AuditTrailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditLogService {

  Page<AuditTrailResponse> findByParams(AuditTrailRequest auditTrailRequest, Pageable pageable);
}
