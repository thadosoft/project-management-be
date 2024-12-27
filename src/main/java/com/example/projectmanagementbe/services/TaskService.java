package com.example.projectmanagementbe.services;

import com.example.projectmanagementbe.dto.requests.creates.AssignmentCreateRequest;
import com.example.projectmanagementbe.dto.requests.creates.TaskCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.TaskUpdateRequest;
import com.example.projectmanagementbe.dto.responses.TaskResponse;
import com.example.projectmanagementbe.entities.TaskEntity;
import java.util.List;

public interface TaskService {
  List<TaskResponse> findAll();

  void create(TaskCreateRequest taskCreateRequest);

  void update(Long id, TaskUpdateRequest TaskUpdateRequest);

  void delete(Long id);

  TaskResponse findById(Long id);

  List<TaskResponse> findByProjectId(Long id);
}
