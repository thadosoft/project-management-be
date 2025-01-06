package com.example.projectmanagementbe.dto.responses;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponse {

  private String id;

  private String status;

  private int taskOrder;

  private ProjectResponse project;

  private LocalDateTime modifiedDate;

  private LocalDateTime createdDate;
}
