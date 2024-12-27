package com.example.projectmanagementbe.dto.requests.updates;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskUpdateRequest {

  private Long projectId;

  private String status;

  private int taskOrder;
}
