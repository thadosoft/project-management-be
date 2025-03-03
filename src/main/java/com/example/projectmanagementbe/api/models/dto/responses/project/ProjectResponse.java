package com.example.projectmanagementbe.api.models.dto.responses.project;

import com.example.projectmanagementbe.api.models.dto.responses.UserResponse;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponse {

  private String id;

  private String name;

  private String description;

  private UserResponse user;

  private String entryBy;

  private LocalDateTime entryDate;

  private String updatedBy;

  private LocalDateTime updatedDate;
}
