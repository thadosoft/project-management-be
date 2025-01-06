package com.example.projectmanagementbe.services.impls;

import com.example.projectmanagementbe.dto.requests.creates.ProjectCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.ProjectUpdateRequest;
import com.example.projectmanagementbe.dto.responses.ProjectResponse;
import com.example.projectmanagementbe.entities.ProjectEntity;
import com.example.projectmanagementbe.mappers.ProjectMapper;
import com.example.projectmanagementbe.repositories.ProjectRepository;
import com.example.projectmanagementbe.services.ProjectService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper;

  @Override
  public List<ProjectResponse> findAll() {
    return projectRepository.findAll().stream().map(projectMapper::toProjectResponse).toList();
  }

  @Override
  public void create(ProjectCreateRequest projectCreateRequest) {
    projectRepository.save(projectMapper.toProjectEntity(projectCreateRequest));
  }

  @Override
  public void update(String id, ProjectUpdateRequest projectUpdateRequest) {
    ProjectEntity projectEntity = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));

    projectMapper.toProjectEntity(projectUpdateRequest, projectEntity);

    projectRepository.save(projectEntity);
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
