package com.example.projectmanagementbe.api.repositories.AuditLog;

import com.example.projectmanagementbe.api.models.AuditTrail;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import org.springframework.data.repository.query.Param;

public interface AuditLogRepository extends JpaRepository<AuditTrail, Long> {
  @Query("SELECT a FROM AuditTrail a WHERE " +
      "(:resource is null or a.resource like %:resource%) " +
      "AND (:startDate is null or a.createdAt >= :startDate) " +
      "AND (:endDate is null or a.createdAt <= :endDate)")
  Page<AuditTrail> getAllByParams(@Param("resource") String resource,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate,
      Pageable pageable);
}
