package com.example.projectmanagementbe.api.models.dto.responses;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

  private String id;

  private String name;

  private String email;

  private String username;

  private String phoneNumber;

  private String role;
}
