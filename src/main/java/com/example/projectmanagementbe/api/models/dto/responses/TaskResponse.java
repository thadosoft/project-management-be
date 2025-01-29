package com.example.projectmanagementbe.api.models.dto.responses;

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

  private String entryBy;

  private LocalDateTime entryDate;

  private String updatedBy;

  private LocalDateTime updatedDate;
}
