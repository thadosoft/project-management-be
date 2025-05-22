package com.example.projectmanagementbe.api.models.dto.requests.WhiteBoard;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateWhiteBoard {
    @NotNull(message = "Employee ID is required")
    private String employeeId;

    @NotNull()
    @Size(max = 255)
    private String projectName;

    private String description;

    private LocalDate deadline;

    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 100)
    private String status;

    @Size(max = 100)
    private String assignBy;
}