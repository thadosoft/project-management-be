package com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchLeaveRequest {

  /** PENDING | APPROVED | REJECTED | CANCELLED. Null = all. */
  private String status;

  private Long employeeId;

  /** ISO date (yyyy-MM-dd). Matches requests that overlap this window. */
  private String startDate;

  private String endDate;
}
