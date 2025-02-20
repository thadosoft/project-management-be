package com.example.projectmanagementbe.api.services.impls.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.AssignmentRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.AssignmentResponse;
import com.example.projectmanagementbe.api.models.project.Assignment;
import com.example.projectmanagementbe.api.models.project.Task;
import com.example.projectmanagementbe.api.mappers.project.AssignmentMapper;
import com.example.projectmanagementbe.api.repositories.project.AssignmentRepository;
import com.example.projectmanagementbe.api.repositories.project.TaskRepository;
import com.example.projectmanagementbe.api.services.project.IAssignmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService implements IAssignmentService {

  private final AssignmentRepository assignmentRepository;
  private final AssignmentMapper assignmentMapper;
  private final TaskRepository taskRepository;

  @Override
  public List<AssignmentResponse> findAll() {
    return assignmentRepository.findAll().stream().map(assignmentMapper::toAssignmentResponse).toList();
  }

  @Override
  public void create(AssignmentRequest assignmentRequest) {
    assignmentRepository.save(assignmentMapper.toAssignmentEntity(assignmentRequest));
  }

  @Override
  public void update(String id, AssignmentRequest assignmentRequest) {
    if (assignmentRequest.getOldAssignmentOrder() != null) {
      Assignment assignmentEntity = assignmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found"));

      if (assignmentRequest.getOldTaskId() != null &&
          !assignmentRequest.getTaskId().equals(assignmentRequest.getOldTaskId())) {
        //move to other task
        List<Assignment> assignmentsInNewTask = assignmentRepository.findByTask_Id(assignmentRequest.getTaskId());
        List<Assignment> assignmentsInOldTask = assignmentRepository.findByTask_Id(assignmentRequest.getOldTaskId());
        Task task = taskRepository.findById(assignmentRequest.getTaskId()).orElseThrow(() -> new RuntimeException("Task not found"));

        for (Assignment assignment : assignmentsInOldTask) {
          if (assignment.getAssignmentOrder() > assignmentRequest.getOldAssignmentOrder()) {
            assignment.setAssignmentOrder(assignment.getAssignmentOrder() - 1);
          }
        }

        if (assignmentRequest.getAssignmentOrder() > assignmentsInNewTask.size()) {
          //move to final position
          assignmentMapper.toAssignmentEntity(assignmentRequest, assignmentEntity);
          assignmentEntity.setTask(task);
        } else {
          //move to non-final position
          for (Assignment assignment : assignmentsInNewTask) {
            if (assignment.getAssignmentOrder() >= assignmentRequest.getAssignmentOrder()) {
              assignment.setAssignmentOrder(assignment.getAssignmentOrder() + 1);
            }
          }
          assignmentMapper.toAssignmentEntity(assignmentRequest, assignmentEntity);
          assignmentEntity.setTask(task);
        }

        assignmentsInNewTask.add(assignmentEntity);
        assignmentRepository.saveAll(assignmentsInNewTask);
      } else {
        //move to same task
        List<Assignment> assignmentsInTask = assignmentRepository.findByTask_Id(assignmentRequest.getTaskId());
        int oldAssignmentOrder = assignmentRequest.getOldAssignmentOrder();
        int newAssignmentOrder = assignmentRequest.getAssignmentOrder();

        if (oldAssignmentOrder > newAssignmentOrder) {
          //move up
          for (Assignment assignment : assignmentsInTask) {
            if (assignment.getId().equals(id)) {
              assignment.setAssignmentOrder(newAssignmentOrder);
            } else if (assignment.getAssignmentOrder() >= newAssignmentOrder &&
                assignment.getAssignmentOrder() < oldAssignmentOrder) {
              assignment.setAssignmentOrder(assignment.getAssignmentOrder() + 1);
            }
          }
        } else {
          //move down
          for (Assignment assignment : assignmentsInTask) {
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
    } else {
      Assignment assignment = assignmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found"));
      assignmentMapper.toAssignmentEntity(assignmentRequest, assignment);

      assignmentRepository.save(assignment);
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
