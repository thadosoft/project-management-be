package com.example.projectmanagementbe.dto.responses;

import com.example.projectmanagementbe.entities.Auditable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

  private Long id;

  private String name;

  private String email;

  private String username;

  private String phoneNumber;

  private String role;

  private LocalDateTime modifiedDate;

  private LocalDateTime createdDate;
}
