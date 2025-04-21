package com.example.projectmanagementbe.api.models.AuditLog.responses.log;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditTrailResponse {
    private Long id;

    private String username;

    private String action;

    private String resource;

    private String details;
}
