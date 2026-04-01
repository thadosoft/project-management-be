package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.api.models.lunch.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByUserIdAndOrderDate(String userId, LocalDate orderDate);

    boolean existsByUserIdAndOrderDate(String userId, LocalDate orderDate);

    List<Order> findByOrderDate(LocalDate orderDate);

    List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
}