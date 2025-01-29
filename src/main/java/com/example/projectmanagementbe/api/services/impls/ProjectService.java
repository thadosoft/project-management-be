package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.models.dto.requests.ProjectRequest;
import com.example.projectmanagementbe.api.models.dto.responses.ProjectResponse;
import com.example.projectmanagementbe.api.models.Project;
import com.example.projectmanagementbe.api.mappers.ProjectMapper;
import com.example.projectmanagementbe.api.repositories.ProjectRepository;
import com.example.projectmanagementbe.api.services.IProjectService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService implements IProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper;

  @Override
  public List<ProjectResponse> findAll() {
    return projectRepository.findAll().stream().map(projectMapper::toProjectResponse).toList();
  }

  @Override
  public void create(ProjectRequest projectRequest) {
    projectRepository.save(projectMapper.toProjectEntity(projectRequest));
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
