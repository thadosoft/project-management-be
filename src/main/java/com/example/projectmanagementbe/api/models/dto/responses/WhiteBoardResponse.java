package com.example.projectmanagementbe.api.models.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WhiteBoardResponse {
    private Long id;
    private Long employeeId;
    private String projectName;
    private String description;
    private LocalDate deadline;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String assignBy;
}