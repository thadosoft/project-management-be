package com.example.projectmanagementbe.api.repositories.quotation;

import com.example.projectmanagementbe.api.models.QuotationRequest;
import com.example.projectmanagementbe.api.models.dto.requests.Employee.EmployeeRequest;
import com.example.projectmanagementbe.api.models.dto.requests.quotation.CreateQuotationRequest;
import com.example.projectmanagementbe.api.models.dto.requests.quotation.SearchQuotationRequest;
import com.example.projectmanagementbe.api.models.dto.responses.Employee.EmployeeResponse;
import com.example.projectmanagementbe.api.models.dto.responses.quotation.QuotationResponse;
import com.example.projectmanagementbe.api.models.employee.CaptureDatum;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuotationRequestRepository extends JpaRepository<QuotationRequest, Long> {

  @Query("SELECT r FROM QuotationRequest r " +
      "WHERE (:requesterName IS NULL OR LOWER(r.requesterName) LIKE LOWER(CONCAT('%', :requesterName, '%'))) " +
      " AND(:receiverName IS NULL OR LOWER(r.receiverName) LIKE LOWER(CONCAT('%', :receiverName, '%'))) " +
      " AND(:title IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
      "AND (:startDate IS NULL OR :endDate IS NULL OR r.createdAt BETWEEN :startDate AND :endDate)")
  Page<QuotationRequest> findByParams(
      @Param("title") String title,
      @Param("requesterName") String requesterName,
      @Param("receiverName") String receiverName,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate,
      Pageable pageable
  );
}