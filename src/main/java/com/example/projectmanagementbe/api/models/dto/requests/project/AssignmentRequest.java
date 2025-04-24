package com.example.projectmanagementbe.api.models.dto.requests.project;

import com.example.projectmanagementbe.auth.enums.AssignmentStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

  private AssignmentStatus status_type;

  private LocalDateTime start_date;
  private LocalDateTime end_date;

}
