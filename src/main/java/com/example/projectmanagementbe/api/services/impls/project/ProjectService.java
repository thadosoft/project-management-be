package com.example.projectmanagementbe.api.services.impls.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.ProjectRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.ProjectResponse;
import com.example.projectmanagementbe.api.models.project.Project;
import com.example.projectmanagementbe.api.mappers.project.ProjectMapper;
import com.example.projectmanagementbe.api.repositories.project.ProjectRepository;
import com.example.projectmanagementbe.api.services.project.IProjectService;
import com.example.projectmanagementbe.auth.configs.application.StorageConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService implements IProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper;
  private final StorageConfig storageConfig;

  @Override
  public List<ProjectResponse> findAll() {
    return projectRepository.findAll().stream().map(projectMapper::toProjectResponse).toList();
  }

  @Override
  public ProjectResponse create(ProjectRequest projectRequest) throws IOException {
    if (projectRepository.findByName(projectRequest.getName()).isPresent()) {
      throw new RuntimeException("Project name already exists");
    }

    Files.createDirectories(Path.of(storageConfig.getDirectory()).resolve(projectRequest.getName()));
    return projectMapper.toProjectResponse(projectRepository.save(projectMapper.toProjectEntity(projectRequest)));
  }

  @Override
  public void update(String id, ProjectRequest projectRequest) {
    Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));

    projectMapper.toProjectEntity(projectRequest, project);

    projectRepository.save(project);
  }

  @Override
  public void delete(String id) {
    if (projectRepository.existsById(id)) {
      projectRepository.deleteById(id);
    } else {
      throw new RuntimeException("Project not found");
    }
  }

  @Override
  public ProjectResponse findById(String id) {
    return projectMapper.toProjectResponse(projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found")));
  }
}
