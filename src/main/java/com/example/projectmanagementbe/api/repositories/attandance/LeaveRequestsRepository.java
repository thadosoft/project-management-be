package com.example.projectmanagementbe.api.repositories.attandance;

import com.example.projectmanagementbe.api.enums.LeaveStatus;
import com.example.projectmanagementbe.api.models.LeaveRequests;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LeaveRequestsRepository
    extends JpaRepository<LeaveRequests, Long>, JpaSpecificationExecutor<LeaveRequests> {

  Page<LeaveRequests> findByEmployeeIdOrderByCreatedAtDesc(Long employeeId, Pageable pageable);

  Page<LeaveRequests> findByStatusOrderByCreatedAtDesc(LeaveStatus status, Pageable pageable);

  @Query("SELECT COUNT(r) FROM LeaveRequests r "
      + "WHERE r.employeeId = :employeeId "
      + "AND r.status IN :statuses "
      + "AND r.startDate <= :endDate "
      + "AND r.endDate >= :startDate")
  long countOverlapping(
      @Param("employeeId") Long employeeId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      @Param("statuses") Collection<LeaveStatus> statuses
  );

  @Query("SELECT COUNT(r) FROM LeaveRequests r "
      + "WHERE r.employeeId = :employeeId "
      + "AND r.status IN :statuses "
      + "AND r.startDate <= :endDate "
      + "AND r.endDate >= :startDate "
      + "AND r.id <> :excludeId")
  long countOverlappingExcludingId(
      @Param("employeeId") Long employeeId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      @Param("statuses") Collection<LeaveStatus> statuses,
      @Param("excludeId") Long excludeId
  );
}
