package com.example.projectmanagementbe.api.services.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.ProjectRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.ProjectResponse;
import java.io.IOException;
import java.util.List;

public interface IProjectService {
  List<ProjectResponse> findAll();

  void create(ProjectRequest projectRequest) throws IOException;

  void update(String id, ProjectRequest ProjectRequest);

  void delete(String id);

  ProjectResponse findById(String id);
}
