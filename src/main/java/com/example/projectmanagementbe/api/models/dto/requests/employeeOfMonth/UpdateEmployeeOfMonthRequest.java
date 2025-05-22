package com.example.projectmanagementbe.api.models.dto.requests.employeeOfMonth;

import com.example.projectmanagementbe.api.models.employee.Employee;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter

public class UpdateEmployeeOfMonthRequest {

    private Employee employee;

    private LocalDate monthYear;

    private String reason;

    private Integer month;

    private Integer year;

    private LocalDateTime awardDate;
}