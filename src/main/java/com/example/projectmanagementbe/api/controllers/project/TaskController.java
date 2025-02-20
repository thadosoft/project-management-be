package com.example.projectmanagementbe.api.controllers.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.TaskRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.TaskResponse;
import com.example.projectmanagementbe.api.services.project.ITaskService;
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
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final ITaskService taskService;

  @GetMapping
  public ResponseEntity<List<TaskResponse>> findAll() {
    return ResponseEntity.ok(taskService.findAll());
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody TaskRequest taskRequest) {
    taskService.create(taskRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable String id, @RequestBody TaskRequest taskRequest) {
    taskService.update(id, taskRequest);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    taskService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(taskService.findById(id));
  }

  @GetMapping("/project/{id}")
  public ResponseEntity<List<TaskResponse>> findByProjectId(@PathVariable String id) {
    return ResponseEntity.ok(taskService.findByProjectId(id));
  }
}
