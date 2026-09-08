package com.example.projectmanagementbe.api.models.dto.requests.LeaveBalance;

import lombok.Getter;
import lombok.Setter;

/** OFM-only adjustment of a yearly leave balance. Null fields are left unchanged. */
@Getter
@Setter
public class UpdateLeaveBalanceRequest {

  private Double entitled;

  private Double used;
}
