package com.example.projectmanagementbe.services;

import com.example.projectmanagementbe.dto.requests.creates.TaskCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.TaskUpdateRequest;
import com.example.projectmanagementbe.dto.responses.TaskResponse;
import java.util.List;

public interface TaskService {
  List<TaskResponse> findAll();

  void create(TaskCreateRequest taskCreateRequest);

  void update(String id, TaskUpdateRequest TaskUpdateRequest);

  void delete(String id);

  TaskResponse findById(String id);

  List<TaskResponse> findByProjectId(String id);
}
