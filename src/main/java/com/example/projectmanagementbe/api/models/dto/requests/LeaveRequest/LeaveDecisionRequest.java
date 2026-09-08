package com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest;

import lombok.Getter;
import lombok.Setter;

/** Payload for approve / reject. decisionNote is required when rejecting. */
@Getter
@Setter
public class LeaveDecisionRequest {

  private String decisionNote;
}
