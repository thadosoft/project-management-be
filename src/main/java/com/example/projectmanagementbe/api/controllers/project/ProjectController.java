package com.example.projectmanagementbe.api.controllers.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.ProjectRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.ProjectResponse;
import com.example.projectmanagementbe.api.services.project.IProjectService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

  private final IProjectService projectService;

  @GetMapping
  public ResponseEntity<List<ProjectResponse>> findAll() {
    return ResponseEntity.ok(projectService.findAll());
  }

  @PostMapping
  public ResponseEntity<ProjectResponse> create(@RequestBody ProjectRequest projectRequest) throws IOException {
    return ResponseEntity.ok(projectService.create(projectRequest));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable String id, @RequestBody ProjectRequest projectRequest) {
    projectService.update(id, projectRequest);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    projectService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProjectResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(projectService.findById(id));
  }
}
