package com.example.projectmanagementbe.api.models.dto.responses.lunch;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class WeeklyStatsResponse {

    private String startDate;

    private String endDate;

    private List<WeeklyUserStats> stats;

    private BigDecimal grandTotal;
}
