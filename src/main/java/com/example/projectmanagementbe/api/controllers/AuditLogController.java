package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.AuditLog.requests.log.AuditTrailRequest;
import com.example.projectmanagementbe.api.models.AuditLog.responses.log.AuditTrailResponse;
import com.example.projectmanagementbe.api.services.AuditLogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit-logs")
@AllArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @PostMapping("/search-params")
    public Page<AuditTrailResponse> findByParams(@RequestBody AuditTrailRequest auditTrailRequest, Pageable pageable) {
        return auditLogService.findByParams(auditTrailRequest, pageable);
    }

    @GetMapping
    public List<AuditTrailResponse> get6LatestLogs() {
        return auditLogService.get6LatestLogs();
    }
}