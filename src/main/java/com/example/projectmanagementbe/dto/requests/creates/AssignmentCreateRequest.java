package com.example.projectmanagementbe.dto.requests.creates;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentCreateRequest {

  private Long assignerId;

  private Long receiverId;

  private Long taskId;

  private String title;

  private String description;

  private String status;

  private int assignmentOrder;
}
