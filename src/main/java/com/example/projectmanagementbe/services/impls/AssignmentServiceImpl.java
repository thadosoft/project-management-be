package com.example.projectmanagementbe.services.impls;

import com.example.projectmanagementbe.dto.requests.creates.AssignmentCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.AssignmentUpdateRequest;
import com.example.projectmanagementbe.dto.responses.AssignmentResponse;
import com.example.projectmanagementbe.entities.AssignmentEntity;
import com.example.projectmanagementbe.entities.TaskEntity;
import com.example.projectmanagementbe.mappers.AssignmentMapper;
import com.example.projectmanagementbe.repositories.AssignmentRepository;
import com.example.projectmanagementbe.repositories.TaskRepository;
import com.example.projectmanagementbe.services.AssignmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

  private final AssignmentRepository assignmentRepository;
  private final AssignmentMapper assignmentMapper;
  private final TaskRepository taskRepository;

  @Override
  public List<AssignmentResponse> findAll() {
    return assignmentRepository.findAll().stream().map(assignmentMapper::toAssignmentResponse).toList();
  }

  @Override
  public void create(AssignmentCreateRequest assignmentCreateRequest) {
    assignmentRepository.save(assignmentMapper.toAssignmentEntity(assignmentCreateRequest));
  }

  @Override
  public void update(String id, AssignmentUpdateRequest assignmentUpdateRequest) {
    AssignmentEntity assignmentEntity = assignmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found"));

    if (assignmentUpdateRequest.getNewTaskId() != null) {
      //move to other task
      List<AssignmentEntity> assignmentsInNewTask = assignmentRepository.findByTask_Id(assignmentUpdateRequest.getNewTaskId());
      List<AssignmentEntity> assignmentsInOldTask = assignmentRepository.findByTask_Id(assignmentUpdateRequest.getOldTaskId());
      TaskEntity taskEntity = taskRepository.findById(assignmentUpdateRequest.getNewTaskId()).orElseThrow(() -> new RuntimeException("Task not found"));

      for (AssignmentEntity assignment : assignmentsInOldTask) {
        if (assignment.getAssignmentOrder() > assignmentUpdateRequest.getOldAssignmentOrder()) {
          assignment.setAssignmentOrder(assignment.getAssignmentOrder() - 1);
        }
      }

      if (assignmentUpdateRequest.getNewAssignmentOrder() > assignmentsInNewTask.size()) {
        //move to final position
        assignmentMapper.toAssignmentEntity(assignmentUpdateRequest, assignmentEntity);
        assignmentEntity.setTask(taskEntity);
      } else {
        //move to non-final position
        for (AssignmentEntity assignment : assignmentsInNewTask) {
          if (assignment.getAssignmentOrder() >= assignmentUpdateRequest.getNewAssignmentOrder()) {
            assignment.setAssignmentOrder(assignment.getAssignmentOrder() + 1);
          }
        }
        assignmentMapper.toAssignmentEntity(assignmentUpdateRequest, assignmentEntity);
        assignmentEntity.setTask(taskEntity);
      }

      assignmentsInNewTask.add(assignmentEntity);
      assignmentRepository.saveAll(assignmentsInNewTask);
    } else {
      //move to same task
      List<AssignmentEntity> assignmentsInTask = assignmentRepository.findByTask_Id(assignmentUpdateRequest.getOldTaskId());
      int oldAssignmentOrder = assignmentUpdateRequest.getOldAssignmentOrder();
      int newAssignmentOrder = assignmentUpdateRequest.getNewAssignmentOrder();

      if (oldAssignmentOrder > newAssignmentOrder) {
        //move up
        for (AssignmentEntity assignment : assignmentsInTask) {
          if (assignment.getId().equals(id)) {
            assignment.setAssignmentOrder(newAssignmentOrder);
          } else if (assignment.getAssignmentOrder() >= newAssignmentOrder &&
              assignment.getAssignmentOrder() < oldAssignmentOrder) {
            assignment.setAssignmentOrder(assignment.getAssignmentOrder() + 1);
          }
        }
      } else {
        //move down
        for (AssignmentEntity assignment : assignmentsInTask) {
          if (assignment.getId().equals(id)) {
            assignment.setAssignmentOrder(newAssignmentOrder);
          } else if (assignment.getAssignmentOrder() <= newAssignmentOrder &&
              assignment.getAssignmentOrder() > oldAssignmentOrder) {
            assignment.setAssignmentOrder(assignment.getAssignmentOrder() - 1);
          }
        }
      }

      assignmentRepository.saveAll(assignmentsInTask);
    }
  }

  @Override
  public void delete(String id) {
    if (assignmentRepository.existsById(id)) {
      assignmentRepository.deleteById(id);
    } else {
      throw new RuntimeException("Assignment not found");
    }
  }

  @Override
  public AssignmentResponse findById(String id) {
    return assignmentMapper.toAssignmentResponse(assignmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found")));
  }

  @Override
  public List<AssignmentResponse> findByProjectId(String id) {
    return assignmentMapper.toAssignmentResponses(assignmentRepository.findByTask_Project_Id(id));
  }
}
