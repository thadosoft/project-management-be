package com.example.projectmanagementbe.api.models.dto.responses.lunch;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TodayOrderSummary {

    private Long orderId;

    private String userId;

    private String userName;

    // The priority-1 item (main dish)
    private String mainDish;

    private BigDecimal mainDishPrice;

    // Number of backup items
    private Integer backupCount;

    private String status;
}