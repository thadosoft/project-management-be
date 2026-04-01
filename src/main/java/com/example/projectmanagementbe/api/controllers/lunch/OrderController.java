package com.example.projectmanagementbe.api.controllers.lunch;

import com.example.projectmanagementbe.api.models.dto.requests.lunch.CreateOrderRequest;
import com.example.projectmanagementbe.api.models.dto.responses.lunch.OrderResponse;
import com.example.projectmanagementbe.api.models.dto.responses.lunch.WeeklyStatsResponse;
import com.example.projectmanagementbe.api.services.lunch.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Create today's order
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
    }

    // Get my order for today
    @GetMapping("/my-today")
    public ResponseEntity<OrderResponse> getMyTodayOrder() {
        OrderResponse order = orderService.getMyTodayOrder();
        if (order == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(order);
    }

    // Update my order for today
    @PutMapping("/my-today")
    public ResponseEntity<OrderResponse> updateMyTodayOrder(@Valid @RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.updateMyTodayOrder(request));
    }

    // Cancel my order for today
    @DeleteMapping("/my-today")
    public ResponseEntity<Void> cancelMyTodayOrder() {
        orderService.cancelMyTodayOrder();
        return ResponseEntity.noContent().build();
    }

    // View all orders for today (everyone can see)
    @GetMapping("/today")
    public ResponseEntity<List<OrderResponse>> getTodayOrders() {
        return ResponseEntity.ok(orderService.getTodayOrders());
    }

    // Get orders by specific date
    @GetMapping("/by-date")
    public ResponseEntity<List<OrderResponse>> getOrdersByDate(@RequestParam String date) {
        return ResponseEntity.ok(orderService.getOrdersByDate(LocalDate.parse(date)));
    }

    @GetMapping("/weekly-stats")
    public ResponseEntity<WeeklyStatsResponse> getWeeklyStats(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return ResponseEntity.ok(orderService.getWeeklyStats(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        ));
    }
}