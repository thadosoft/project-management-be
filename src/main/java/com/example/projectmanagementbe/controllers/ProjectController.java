package com.example.projectmanagementbe.controllers;

import com.example.projectmanagementbe.dto.requests.creates.ProjectCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.ProjectUpdateRequest;
import com.example.projectmanagementbe.dto.responses.ProjectResponse;
import com.example.projectmanagementbe.services.ProjectService;
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

  private final ProjectService projectService;

  @GetMapping
  public ResponseEntity<List<ProjectResponse>> findAll() {
    return ResponseEntity.ok(projectService.findAll());
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody ProjectCreateRequest projectCreateRequest) {
    projectService.create(projectCreateRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ProjectUpdateRequest projectUpdateRequest) {
    projectService.update(id, projectUpdateRequest);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    projectService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProjectResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(projectService.findById(id));
  }
}
