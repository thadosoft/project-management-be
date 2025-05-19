package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.api.models.LeaveRequests;
import com.example.projectmanagementbe.api.models.WhiteBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface WhiteBoardRepository extends JpaRepository<WhiteBoard, Long>, JpaSpecificationExecutor<LeaveRequests> {
    @Query("SELECT r FROM WhiteBoard r " +
            "WHERE  (:startDate IS NULL OR :endDate IS NULL OR r.createdAt BETWEEN :startDate AND :endDate)")
    Page<WhiteBoard> findByParams(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

}