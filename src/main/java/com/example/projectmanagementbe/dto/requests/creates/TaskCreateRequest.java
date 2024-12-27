package com.example.projectmanagementbe.dto.requests.creates;

import com.example.projectmanagementbe.dto.responses.UserResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateRequest {

  private Long projectId;

  private String status;

  private int taskOrder;
}
