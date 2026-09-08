package com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLeaveRequest {

  private LocalDate startDate;

  private LocalDate endDate;

  private Double dayPortion;

  private String leaveType;

  private String reason;
}
