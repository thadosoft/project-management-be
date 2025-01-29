package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.dto.requests.ProjectRequest;
import com.example.projectmanagementbe.api.models.dto.responses.ProjectResponse;
import java.util.List;

public interface IProjectService {
  List<ProjectResponse> findAll();

  void create(ProjectRequest projectRequest);

  void update(String id, ProjectRequest ProjectRequest);

  void delete(String id);

  ProjectResponse findById(String id);
}
