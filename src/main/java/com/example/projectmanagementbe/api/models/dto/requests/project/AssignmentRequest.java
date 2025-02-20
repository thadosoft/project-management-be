package com.example.projectmanagementbe.api.models.dto.requests.project;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentRequest {

  @NotBlank(message = "Assignment title cannot be blank")
  private String title;

  private String description;

  @Min(value = 1, message = "Assignment order cannot be less than one")
  private int assignmentOrder;

  private Integer oldAssignmentOrder;

  @NotNull(message = "Task cannot be null")
  private String taskId;

  private String oldTaskId;

  private String assignerId;

  private String receiverId;
}
