package com.example.projectmanagementbe.dto.responses;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponse {

  private Long id;

  private String name;

  private String description;

  private UserResponse user;

  private LocalDateTime modifiedDate;

  private LocalDateTime createdDate;
}
