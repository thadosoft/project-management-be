package com.example.projectmanagementbe.api.models;

import com.example.projectmanagementbe.api.enums.LeaveStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "leave_requests")
public class LeaveRequests extends Auditable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "employee_id", nullable = false)
  private Long employeeId;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  /** 0.25, 0.5 or 1.0 - only meaningful when startDate equals endDate. */
  @Column(name = "day_portion", nullable = false)
  private Double dayPortion = 1.0;

  /** Actual chargeable working days, computed on create/update. */
  @Column(name = "number_of_days", nullable = false)
  private Double numberOfDays = 0.0;

  @Column(name = "leave_type")
  private String leaveType;

  @Column(name = "reason")
  private String reason;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private LeaveStatus status = LeaveStatus.PENDING;

  @Column(name = "approver_id", columnDefinition = "CHAR(36)")
  private String approverId;

  @Column(name = "approved_at")
  private LocalDateTime approvedAt;

  @Column(name = "decision_note", length = 500)
  private String decisionNote;
}
