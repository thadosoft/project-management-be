package com.example.projectmanagementbe.dto.responses;

import com.example.projectmanagementbe.entities.Auditable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponse {

  private Long id;

  private String name;

  private LocalDateTime modifiedDate;

  private LocalDateTime createdDate;
}
