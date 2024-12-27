package com.example.projectmanagementbe.dto.requests.creates;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectCreateRequest {

  private Long userId;

  private String name;

  private String description;
}
