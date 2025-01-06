package com.example.projectmanagementbe.dto.responses;

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

  private LocalDateTime modifiedDate;

  private LocalDateTime createdDate;
}
