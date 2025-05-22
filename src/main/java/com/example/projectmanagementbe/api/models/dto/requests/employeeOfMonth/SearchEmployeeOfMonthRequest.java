package com.example.projectmanagementbe.api.models.dto.requests.employeeOfMonth;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchEmployeeOfMonthRequest {

    private String name;

    private String startDate;

    private String endDate;
}
