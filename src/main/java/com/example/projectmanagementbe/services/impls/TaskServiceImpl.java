package com.example.projectmanagementbe.services.impls;

import com.example.projectmanagementbe.dto.requests.creates.TaskCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.TaskUpdateRequest;
import com.example.projectmanagementbe.dto.responses.TaskResponse;
import com.example.projectmanagementbe.entities.TaskEntity;
import com.example.projectmanagementbe.mappers.TaskMapper;
import com.example.projectmanagementbe.repositories.TaskRepository;
import com.example.projectmanagementbe.services.TaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;

  @Override
  public List<TaskResponse> findAll() {
    return taskRepository.findAll().stream().map(taskMapper::toTaskResponse).toList();
  }

  @Override
  public void create(TaskCreateRequest taskCreateRequest) {
    taskRepository.save(taskMapper.toTaskEntity(taskCreateRequest));
  }

  @Override
  public void update(String id, TaskUpdateRequest taskUpdateRequest) {
    TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

    taskMapper.toTaskEntity(taskUpdateRequest, taskEntity);

    taskRepository.save(taskEntity);
  }

  @Override
  public void delete(String id) {
    if (taskRepository.existsById(id)) {
      taskRepository.deleteById(id);
    } else {
      throw new RuntimeException("Task not found");
    }
  }

  @Override
  public TaskResponse findById(String id) {
    return taskMapper.toTaskResponse(taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found")));
  }

  @Override
  public List<TaskResponse> findByProjectId(String id) {
    return taskMapper.toTaskResponses(taskRepository.findByProject_Id(id));
  }
}
