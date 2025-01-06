package com.example.projectmanagementbe.dto.requests.creates;

import com.example.projectmanagementbe.dto.responses.UserResponse;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateRequest {

  private String projectId;

  private String status;

  private int taskOrder;
}
