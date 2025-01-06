package com.example.projectmanagementbe.dto.requests.creates;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectCreateRequest {

  private String userId;

  private String name;

  private String description;
}
