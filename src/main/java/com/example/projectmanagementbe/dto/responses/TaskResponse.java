package com.example.projectmanagementbe.dto.responses;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponse {

  private Long id;

  private String status;

  private int taskOrder;

  private ProjectResponse project;

  private List<AssignmentResponse> assignments;

  private LocalDateTime modifiedDate;

  private LocalDateTime createdDate;
}
