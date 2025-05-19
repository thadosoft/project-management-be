package com.example.projectmanagementbe.api.models.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LeaveResponse {
    private Long id;

    private Long employeeId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String leaveType;

    private String reason;

    private String status;
}
