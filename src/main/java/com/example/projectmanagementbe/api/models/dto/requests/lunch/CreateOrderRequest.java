package com.example.projectmanagementbe.api.models.dto.requests.lunch;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    // Ordered by priority: index 0 = priority 1 (most wanted)
    @NotEmpty(message = "Must select at least 1 item")
    @Size(max = 5, message = "Maximum 5 items allowed")
    @Valid
    private List<OrderItemRequest> items;
}