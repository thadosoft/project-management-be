package com.example.projectmanagementbe.controllers;

import com.example.projectmanagementbe.dto.requests.creates.AssignmentCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.AssignmentUpdateRequest;
import com.example.projectmanagementbe.dto.responses.AssignmentResponse;
import com.example.projectmanagementbe.services.AssignmentService;
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

  private final AssignmentService assignmentService;

  @GetMapping
  public ResponseEntity<List<AssignmentResponse>> findAll() {
    return ResponseEntity.ok(assignmentService.findAll());
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody AssignmentCreateRequest assignmentCreateRequest) {
    assignmentService.create(assignmentCreateRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AssignmentUpdateRequest assignmentUpdateRequest) {
    assignmentService.update(id, assignmentUpdateRequest);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    assignmentService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<AssignmentResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(assignmentService.findById(id));
  }
}
