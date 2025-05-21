package com.example.projectmanagementbe.api.repositories.attandance;

import com.example.projectmanagementbe.api.models.employee.CaptureDatum;
import com.example.projectmanagementbe.api.models.employee.Employee;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface CaptureDatumRepository extends JpaRepository<CaptureDatum, Long> {

  boolean existsByCaptureId(Long captureId);

  @Query("SELECT r FROM CaptureDatum r " +
      "WHERE (:keyword IS NULL OR LOWER(r.personName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
      "AND (:startDate IS NULL OR :endDate IS NULL OR " +
      "FUNCTION('STR_TO_DATE', r.time, '%Y-%m-%d %H:%i:%s') BETWEEN :startDate AND :endDate)")
  Page<CaptureDatum> findByParams(
      @Param("keyword") String keyword,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate,
      Pageable pageable
  );
  @Procedure(procedureName = "RetriveTotalShiftDay")
  Object[] getAttendanceData(@Param("work_date_param") String startDate,
      @Param("emp_code_param") String empCode);

  List<CaptureDatum> findTop6ByOrderByCreatedAtDesc();
}
