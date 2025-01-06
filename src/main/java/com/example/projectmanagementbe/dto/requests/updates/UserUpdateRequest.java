package com.example.projectmanagementbe.dto.requests.updates;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

  private String name;

  private String email;

  private String username;

  private String password;

  private String phoneNumber;

  private String roleId;
}
