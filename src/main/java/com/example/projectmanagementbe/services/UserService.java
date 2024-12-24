package com.example.projectmanagementbe.services;

import com.example.projectmanagementbe.dto.requests.updates.UserUpdateRequest;
import com.example.projectmanagementbe.dto.responses.UserResponse;
import java.util.List;

public interface UserService {
  List<UserResponse> findAll();

  void update(Long id, UserUpdateRequest UserUpdateRequest);

  void delete(Long id);

  UserResponse findById(Long id);
}
