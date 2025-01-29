package com.example.projectmanagementbe.api.models.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {

  @NotBlank(message = "Task status cannot be blank")
  private String status;

  @Min(value = 1, message = "Task order cannot be less than one")
  private int taskOrder;

  private Integer oldTaskOrder;

  @NotNull(message = "Project cannot be null")
  private String projectId;
}
