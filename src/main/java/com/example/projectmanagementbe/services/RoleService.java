package com.example.projectmanagementbe.services;

import com.example.projectmanagementbe.dto.requests.creates.RoleCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.RoleUpdateRequest;
import com.example.projectmanagementbe.dto.responses.RoleResponse;
import java.util.List;

public interface RoleService {
  List<RoleResponse> findAll();

  void create(RoleCreateRequest RoleCreateRequest);

  void update(String id, RoleUpdateRequest RoleUpdateRequest);

  void delete(String id);

  RoleResponse findById(String id);
}
