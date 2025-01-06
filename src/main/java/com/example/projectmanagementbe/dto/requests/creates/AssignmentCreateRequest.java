package com.example.projectmanagementbe.dto.requests.creates;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentCreateRequest {

  private String title;

  private String description;

  private String status;

  private int assignmentOrder;

  private String taskId;

  private String assignerId;

  private String receiverId;
}
