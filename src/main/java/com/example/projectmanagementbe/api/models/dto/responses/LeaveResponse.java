package com.example.projectmanagementbe.api.models.dto.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveResponse {

  private Long id;

  private Long employeeId;

  private String employeeName;

  private LocalDate startDate;

  private LocalDate endDate;

  private Double dayPortion;

  private Double numberOfDays;

  private String leaveType;

  private String reason;

  private String status;

  private String approverId;

  private String approverName;

  private LocalDateTime approvedAt;

  private String decisionNote;

  /** Remaining balance of the requester for the year of the request. */
  private Double remainingLeave;

  private LocalDateTime createdAt;
}
