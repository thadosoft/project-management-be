package com.example.projectmanagementbe.api.controllers.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.AssignmentRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.AssignmentResponse;
import com.example.projectmanagementbe.api.services.project.IAssignmentService;
import java.util.List;

import com.example.projectmanagementbe.auth.enums.AssignmentStatus;
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
@RequestMapping("/api/v1/assignments")
@RequiredArgsConstructor
public class AssignmentController {

  private final IAssignmentService assignmentService;

  @GetMapping
  public ResponseEntity<List<AssignmentResponse>> findAll() {
    return ResponseEntity.ok(assignmentService.findAll());
  }

  @PostMapping
  public ResponseEntity<AssignmentResponse> create(@RequestBody AssignmentRequest assignmentRequest) {
    return ResponseEntity.ok(assignmentService.create(assignmentRequest));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable String id, @RequestBody AssignmentRequest assignmentRequest) {
    assignmentService.update(id, assignmentRequest);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    assignmentService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<AssignmentResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(assignmentService.findById(id));
  }


  @GetMapping("/project/{id}")
  public ResponseEntity<List<AssignmentResponse>> findByProjectId(@PathVariable String id) {
    return ResponseEntity.ok(assignmentService.findByProjectId(id));
  }

  @GetMapping("/statuses")
  public ResponseEntity<AssignmentStatus[]> getStatuses() {
    return ResponseEntity.ok(AssignmentStatus.values());
  }
}