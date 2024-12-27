package com.example.projectmanagementbe.dto.requests.updates;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectUpdateRequest {

  private Long userId;

  private String name;

  private String description;
}
