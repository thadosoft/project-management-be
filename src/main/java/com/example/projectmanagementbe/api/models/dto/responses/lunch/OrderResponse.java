package com.example.projectmanagementbe.api.models.dto.responses.lunch;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponse {

    private Long id;

    private String userId;

    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    private String status;

    private List<OrderItemResponse> items;

    // Total price of priority-1 item (the one that will be served)
    private BigDecimal totalPrice;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}