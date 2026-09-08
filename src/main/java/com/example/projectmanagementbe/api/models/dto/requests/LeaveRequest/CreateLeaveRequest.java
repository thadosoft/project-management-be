package com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLeaveRequest {

  private LocalDate startDate;

  private LocalDate endDate;

  /** 0.25, 0.5 or 1.0. Only allowed when startDate equals endDate. Defaults to 1.0. */
  private Double dayPortion;

  /** ANNUAL (default), UNPAID, SICK, OTHER. */
  private String leaveType;

  private String reason;
}
