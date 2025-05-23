package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.api.models.EmployeeOfMonth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface EmployeeOfMonthRepository extends JpaRepository<EmployeeOfMonth, Long>, JpaSpecificationExecutor<EmployeeOfMonth> {
    @Query("SELECT r FROM EmployeeOfMonth r " +
            "WHERE (:requesterName IS NULL OR (LOWER(r.employee.fullName) LIKE LOWER(CONCAT('%', :requesterName, '%'))))" +
            "AND (:startDate IS NULL OR :endDate IS NULL OR r.awardDate BETWEEN :startDate AND :endDate)")
    Page<EmployeeOfMonth> findByParams(
            @Param("requesterName") String requesterName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}