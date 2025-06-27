package com.example.projectmanagementbe.api.services.impls.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.ProjectRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.ProjectResponse;
import com.example.projectmanagementbe.api.models.project.Project;
import com.example.projectmanagementbe.api.mappers.project.ProjectMapper;
import com.example.projectmanagementbe.api.repositories.project.ProjectRepository;
import com.example.projectmanagementbe.api.services.mail.MailService;
import com.example.projectmanagementbe.api.services.project.IProjectService;
import com.example.projectmanagementbe.auth.configs.application.StorageConfig;
import com.example.projectmanagementbe.auth.models.User;
import com.example.projectmanagementbe.auth.repositories.UserRepository;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService implements IProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper;
  private final StorageConfig storageConfig;
  private final UserRepository userRepository;
  private final MailService mailService;

  @Override
  public List<ProjectResponse> findAll() {
    return projectRepository.findAll().stream().map(projectMapper::toProjectResponse).toList();
  }

  @Override
  public ProjectResponse create(ProjectRequest projectRequest) throws IOException {
    if (projectRepository.findByName(projectRequest.getName()).isPresent()) {
      throw new RuntimeException("Project name already exists");
    }

    // 1. Tạo folder lưu trữ
    Files.createDirectories(Path.of(storageConfig.getDirectory()).resolve(projectRequest.getName()));

    // 2. Lưu project
    Project savedProject = projectRepository.save(projectMapper.toProjectEntity(projectRequest));

    // 3. Lấy email của tất cả người dùng (lọc email null)
    List<String> emails = userRepository.findAll().stream()
            .map(User::getEmail)
            .filter(email -> email != null && !email.isEmpty())
            .collect(Collectors.toList());

    // 4. Gửi mail
    try {
      mailService.sendProjectCreationNotification(emails, savedProject.getName());
    } catch (MessagingException e) {
      // Có thể log lỗi hoặc ném RuntimeException nếu cần
      throw new RuntimeException("Failed to send project creation email", e);
    }

    // 5. Trả về response
    return projectMapper.toProjectResponse(savedProject);
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
    return projectMapper.toProjectResponse(projectRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Project not found")));
  }
}
