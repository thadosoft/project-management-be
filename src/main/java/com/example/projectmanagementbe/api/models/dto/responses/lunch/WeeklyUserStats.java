package com.example.projectmanagementbe.api.models.dto.responses.lunch;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class WeeklyUserStats {

    private String userId;

    private String userName;

    // date string (yyyy-MM-dd) -> cost for that day (priority 1 only)
    private Map<String, BigDecimal> dailyCosts;

    private BigDecimal totalWeek;
}
