package com.example.projectmanagementbe.api.models.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequest {

  @NotBlank(message = "Project name cannot be blank")
  private String name;

  private String description;

  @NotNull(message = "User cannot be null")
  private String userId;
}
