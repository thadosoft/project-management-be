package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.models.dto.requests.TaskRequest;
import com.example.projectmanagementbe.api.models.dto.responses.TaskResponse;
import com.example.projectmanagementbe.api.models.Task;
import com.example.projectmanagementbe.api.mappers.TaskMapper;
import com.example.projectmanagementbe.api.repositories.TaskRepository;
import com.example.projectmanagementbe.api.services.ITaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {

  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;

  @Override
  public List<TaskResponse> findAll() {
    return taskRepository.findAll().stream().map(taskMapper::toTaskResponse).toList();
  }

  @Override
  public void create(TaskRequest taskRequest) {
    taskRepository.save(taskMapper.toTaskEntity(taskRequest));
  }

  @Override
  public void update(String id, TaskRequest taskRequest) {
    if (taskRequest.getOldTaskOrder() != null &&
        taskRequest.getOldTaskOrder() != taskRequest.getTaskOrder()) {
      List<Task> tasks = taskRepository.findByProject_Id(taskRequest.getProjectId());
      int newTaskOrder = taskRequest.getTaskOrder();
      int oldTaskOrder = taskRequest.getOldTaskOrder();

      if (oldTaskOrder > newTaskOrder) {
        for (Task task : tasks) {
          if (task.getId().equals(id)) {
            task.setTaskOrder(newTaskOrder);
          } else if (task.getTaskOrder() >= newTaskOrder &&
              task.getTaskOrder() < oldTaskOrder) {
            task.setTaskOrder(task.getTaskOrder() + 1);
          }
        }
      } else {
        for (Task task : tasks) {
          if (task.getId().equals(id)) {
            task.setTaskOrder(newTaskOrder);
          } else if (task.getTaskOrder() <= newTaskOrder &&
              task.getTaskOrder() > oldTaskOrder) {
            task.setTaskOrder(task.getTaskOrder() - 1);
          }
        }
      }

      taskRepository.saveAll(tasks);
    } else {
      Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
      taskMapper.toTaskEntity(taskRequest, task);

      taskRepository.save(task);
    }
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
