package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.requests.AssignmentRequest;
import com.example.projectmanagementbe.api.models.dto.responses.AssignmentResponse;
import com.example.projectmanagementbe.api.services.IAssignmentService;
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
@RequestMapping("/api/v1/assignments")
@RequiredArgsConstructor
public class AssignmentController {

  private final IAssignmentService IAssignmentService;

  @GetMapping
  public ResponseEntity<List<AssignmentResponse>> findAll() {
    return ResponseEntity.ok(IAssignmentService.findAll());
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody AssignmentRequest assignmentRequest) {
    IAssignmentService.create(assignmentRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable String id, @RequestBody AssignmentRequest assignmentRequest) {
    IAssignmentService.update(id, assignmentRequest);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    IAssignmentService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<AssignmentResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(IAssignmentService.findById(id));
  }


  @GetMapping("/project/{id}")
  public ResponseEntity<List<AssignmentResponse>> findByProjectId(@PathVariable String id) {
    return ResponseEntity.ok(IAssignmentService.findByProjectId(id));
  }
}
