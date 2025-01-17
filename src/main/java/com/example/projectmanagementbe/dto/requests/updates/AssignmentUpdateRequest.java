package com.example.projectmanagementbe.dto.requests.updates;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AssignmentUpdateRequest {

  private String title;

  private String description;

  private String status;

  private int newAssignmentOrder;

  private int oldAssignmentOrder;

  private String oldTaskId;

  private String newTaskId;

  private String assignerId;

  private String receiverId;
}
