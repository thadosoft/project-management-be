package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.api.models.BookLoan;
import com.example.projectmanagementbe.api.models.EmployeeOfMonth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface BookLoanRepository extends JpaRepository<BookLoan, Long>, JpaSpecificationExecutor<BookLoan> {
    @Query("SELECT r FROM BookLoan r " +
            "WHERE (:title IS NULL OR (LOWER(r.bookTitle) LIKE LOWER(CONCAT('%', :title, '%'))))" +
            "AND (:startDate IS NULL OR :endDate IS NULL OR r.createdAt BETWEEN :startDate AND :endDate)")
    Page<BookLoan> findByParams(
            @Param("title") String title,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}