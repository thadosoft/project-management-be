package com.example.projectmanagementbe.dto.requests.updates;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskUpdateRequest {

  private String status;

  private int newTaskOrder;

  private int oldTaskOrder;
}
