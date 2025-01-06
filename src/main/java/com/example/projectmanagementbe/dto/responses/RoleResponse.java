package com.example.projectmanagementbe.dto.responses;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponse {

  private String id;

  private String name;

  private LocalDateTime modifiedDate;

  private LocalDateTime createdDate;
}
