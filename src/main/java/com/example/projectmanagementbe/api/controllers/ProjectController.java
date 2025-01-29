package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.requests.ProjectRequest;
import com.example.projectmanagementbe.api.models.dto.responses.ProjectResponse;
import com.example.projectmanagementbe.api.services.IProjectService;
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

  private final IProjectService IProjectService;

  @GetMapping
  public ResponseEntity<List<ProjectResponse>> findAll() {
    return ResponseEntity.ok(IProjectService.findAll());
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody ProjectRequest projectRequest) {
    IProjectService.create(projectRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable String id, @RequestBody ProjectRequest projectRequest) {
    IProjectService.update(id, projectRequest);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    IProjectService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProjectResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(IProjectService.findById(id));
  }
}
