package com.example.projectmanagementbe.api.models.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveBalanceResponse {

  private Long employeeId;

  private String employeeName;

  private Integer year;

  private Double entitled;

  private Double used;

  /** entitled - used. Can be negative (down to -3 by policy). */
  private Double remaining;
}
