package com.example.projectmanagementbe.api.models.AuditLog.requests.log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditTrailRequest {

    private String resource;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
