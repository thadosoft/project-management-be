package com.example.projectmanagementbe.api.models.dto.responses.lunch;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemResponse {

    private Long menuItemId;

    private String name;

    private BigDecimal price;

    private String category;

    private Integer priority;

    private Integer quantity;

    private String note;
}
