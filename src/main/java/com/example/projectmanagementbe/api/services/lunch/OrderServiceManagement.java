package com.example.projectmanagementbe.api.services.lunch;

import com.example.projectmanagementbe.api.models.dto.requests.lunch.CreateOrderRequest;
import com.example.projectmanagementbe.api.models.dto.requests.lunch.OrderItemRequest;
import com.example.projectmanagementbe.api.models.dto.responses.lunch.OrderItemResponse;
import com.example.projectmanagementbe.api.models.dto.responses.lunch.OrderResponse;
import com.example.projectmanagementbe.api.models.dto.responses.lunch.WeeklyStatsResponse;
import com.example.projectmanagementbe.api.models.dto.responses.lunch.WeeklyUserStats;
import com.example.projectmanagementbe.api.models.lunch.Food;
import com.example.projectmanagementbe.api.models.lunch.Order;
import com.example.projectmanagementbe.api.models.lunch.OrderItem;
import com.example.projectmanagementbe.api.repositories.FoodRepository;
import com.example.projectmanagementbe.api.repositories.OrderRepository;
import com.example.projectmanagementbe.auth.models.User;
import com.example.projectmanagementbe.auth.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceManagement implements OrderService {

    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    private static final LocalTime ORDER_START = LocalTime.of(6, 0);
    private static final LocalTime ORDER_END = LocalTime.of(11, 15);

    @Value("${app.order.time-check-enabled:true}")
    private boolean timeCheckEnabled;

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        validateOrderTime();

        User user = getCurrentUser();
        LocalDate today = LocalDate.now();

        if (orderRepository.existsByUserIdAndOrderDate(user.getId(), today)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You already placed an order today");
        }

        validateNoDuplicateItems(request.getItems());

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(today);
        order.setStatus("PENDING");

        addItemsToOrder(order, request.getItems());

        return toOrderResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse getMyTodayOrder() {
        User user = getCurrentUser();
        return orderRepository.findByUserIdAndOrderDate(user.getId(), LocalDate.now())
                .map(this::toOrderResponse)
                .orElse(null);
    }

    @Override
    @Transactional
    public OrderResponse updateMyTodayOrder(CreateOrderRequest request) {
        validateOrderTime();

        User user = getCurrentUser();
        Order order = orderRepository.findByUserIdAndOrderDate(user.getId(), LocalDate.now())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No order found for today"));

        validateNoDuplicateItems(request.getItems());

        // Flush DELETE before inserting new items
        order.getOrderItems().clear();
        entityManager.flush();

        addItemsToOrder(order, request.getItems());

        return toOrderResponse(orderRepository.save(order));
    }

    @Override
    @Transactional
    public void cancelMyTodayOrder() {
        validateOrderTime();

        User user = getCurrentUser();
        Order order = orderRepository.findByUserIdAndOrderDate(user.getId(), LocalDate.now())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No order found for today"));

        orderRepository.delete(order);
    }

    @Override
    public List<OrderResponse> getTodayOrders() {
        return orderRepository.findByOrderDate(LocalDate.now()).stream()
                .map(this::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getOrdersByDate(LocalDate date) {
        return orderRepository.findByOrderDate(date).stream()
                .map(this::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public WeeklyStatsResponse getWeeklyStats(LocalDate startDate, LocalDate endDate) {
        List<Order> orders = orderRepository.findByOrderDateBetween(startDate, endDate);

        // Group orders by user
        Map<String, List<Order>> ordersByUser = orders.stream()
                .collect(Collectors.groupingBy(o -> o.getUser().getId()));

        // Build date range list (for consistent columns)
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate d = startDate; !d.isAfter(endDate); d = d.plusDays(1)) {
            if (d.getDayOfWeek().getValue() <= 5) { // Mon-Fri only
                dates.add(d);
            }
        }

        List<WeeklyUserStats> statsList = new ArrayList<>();
        BigDecimal grandTotal = BigDecimal.ZERO;

        for (Map.Entry<String, List<Order>> entry : ordersByUser.entrySet()) {
            List<Order> userOrders = entry.getValue();
            User user = userOrders.get(0).getUser();

            WeeklyUserStats stats = new WeeklyUserStats();
            stats.setUserId(user.getId());
            stats.setUserName(user.getName());

            Map<String, BigDecimal> dailyCosts = new LinkedHashMap<>();
            BigDecimal userTotal = BigDecimal.ZERO;

            for (LocalDate date : dates) {
                // Find order for this date
                BigDecimal dayCost = userOrders.stream()
                        .filter(o -> o.getOrderDate().equals(date))
                        .flatMap(o -> o.getOrderItems().stream())
                        .filter(oi -> oi.getPriority() == 1)
                        .findFirst()
                        .map(oi -> oi.getMenuItem().getPrice()
                                .multiply(BigDecimal.valueOf(oi.getQuantity())))
                        .orElse(BigDecimal.ZERO);

                dailyCosts.put(date.toString(), dayCost);
                userTotal = userTotal.add(dayCost);
            }

            stats.setDailyCosts(dailyCosts);
            stats.setTotalWeek(userTotal);
            grandTotal = grandTotal.add(userTotal);

            statsList.add(stats);
        }

        // Sort by name
        statsList.sort(Comparator.comparing(WeeklyUserStats::getUserName));

        WeeklyStatsResponse response = new WeeklyStatsResponse();
        response.setStartDate(startDate.toString());
        response.setEndDate(endDate.toString());
        response.setStats(statsList);
        response.setGrandTotal(grandTotal);

        return response;
    }

    // ─── Private helpers ──────────────────────────────────────────────

    private void validateOrderTime() {
        if (!timeCheckEnabled) return;

        LocalTime now = LocalTime.now();
        if (now.isBefore(ORDER_START) || now.isAfter(ORDER_END)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Order is only allowed between 06:00 and 11:15"
            );
        }
    }

    private void validateNoDuplicateItems(List<OrderItemRequest> items) {
        Set<Long> uniqueIds = items.stream()
                .map(OrderItemRequest::getMenuItemId)
                .collect(Collectors.toSet());
        if (uniqueIds.size() != items.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate menu items are not allowed");
        }
    }

    private void addItemsToOrder(Order order, List<OrderItemRequest> items) {
        for (int i = 0; i < items.size(); i++) {
            OrderItemRequest req = items.get(i);

            Food food = foodRepository.findById(req.getMenuItemId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Menu item not found"
                    ));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(food);
            orderItem.setPriority(i + 1);
            orderItem.setQuantity(req.getQuantity());
            orderItem.setNote(req.getNote());
            order.getOrderItems().add(orderItem);
        }
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    // ─── Mappers ──────────────────────────────────────────────────────

    private OrderResponse toOrderResponse(Order order) {
        OrderResponse res = new OrderResponse();
        res.setId(order.getId());
        res.setUserId(order.getUser().getId());
        res.setUserName(order.getUser().getName());
        res.setOrderDate(order.getOrderDate());
        res.setStatus(order.getStatus());
        res.setCreatedAt(order.getCreatedAt());
        res.setUpdatedAt(order.getUpdatedAt());

        List<OrderItemResponse> items = order.getOrderItems().stream()
                .map(oi -> {
                    OrderItemResponse ir = new OrderItemResponse();
                    ir.setMenuItemId(oi.getMenuItem().getId());
                    ir.setName(oi.getMenuItem().getName());
                    ir.setPrice(oi.getMenuItem().getPrice());
                    ir.setCategory(oi.getMenuItem().getCategory());
                    ir.setPriority(oi.getPriority());
                    ir.setQuantity(oi.getQuantity());
                    ir.setNote(oi.getNote());
                    return ir;
                })
                .collect(Collectors.toList());

        res.setItems(items);

        // Total = sum of (price * quantity) for all items
        items.stream()
                .filter(i -> i.getPriority() == 1)
                .findFirst()
                .ifPresent(i -> res.setTotalPrice(
                        i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity()))
                ));

        return res;
    }
}