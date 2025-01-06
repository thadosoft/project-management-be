package com.example.projectmanagementbe.services;

import com.example.projectmanagementbe.dto.requests.creates.ProjectCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.ProjectUpdateRequest;
import com.example.projectmanagementbe.dto.responses.ProjectResponse;
import java.util.List;

public interface ProjectService {
  List<ProjectResponse> findAll();

  void create(ProjectCreateRequest projectCreateRequest);

  void update(String id, ProjectUpdateRequest ProjectUpdateRequest);

  void delete(String id);

  ProjectResponse findById(String id);
}
