package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.dto.requests.UserRequest;
import com.example.projectmanagementbe.api.models.dto.responses.UserResponse;
import java.util.List;

public interface IUserService {
  List<UserResponse> findAll();

  void update(String id, UserRequest userRequest);

  void delete(String id);

  UserResponse findById(String id);
}
