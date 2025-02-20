package com.example.projectmanagementbe.api.services.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.TaskRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.TaskResponse;
import java.util.List;

public interface ITaskService {
  List<TaskResponse> findAll();

  void create(TaskRequest taskRequest);

  void update(String id, TaskRequest TaskRequest);

  void delete(String id);

  TaskResponse findById(String id);

  List<TaskResponse> findByProjectId(String id);
}
