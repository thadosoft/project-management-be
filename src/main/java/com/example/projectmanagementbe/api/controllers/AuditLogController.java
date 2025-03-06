package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.AuditLog.requests.log.AuditTrailRequest;
import com.example.projectmanagementbe.api.models.AuditLog.responses.log.AuditTrailResponse;
import com.example.projectmanagementbe.api.services.AuditLogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audit-logs")
@AllArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @PostMapping("/search-params")
    public Page<AuditTrailResponse> findByParams(@RequestBody AuditTrailRequest auditTrailRequest, Pageable pageable) {
        return auditLogService.findByParams(auditTrailRequest, pageable);
    }
}