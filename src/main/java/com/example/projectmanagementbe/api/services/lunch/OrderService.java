package com.example.projectmanagementbe.api.services.lunch;

import com.example.projectmanagementbe.api.models.dto.requests.lunch.CreateOrderRequest;
import com.example.projectmanagementbe.api.models.dto.responses.lunch.OrderResponse;
import com.example.projectmanagementbe.api.models.dto.responses.lunch.WeeklyStatsResponse;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    OrderResponse getMyTodayOrder();

    OrderResponse updateMyTodayOrder(CreateOrderRequest request);

    void cancelMyTodayOrder();

    List<OrderResponse> getTodayOrders();

    List<OrderResponse> getOrdersByDate(LocalDate date);

    WeeklyStatsResponse getWeeklyStats(LocalDate startDate, LocalDate endDate);
}