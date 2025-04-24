package com.example.projectmanagementbe.api.services.impls.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.AssignmentRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.AssignmentResponse;
import com.example.projectmanagementbe.api.models.project.Assignment;
import com.example.projectmanagementbe.api.models.project.Task;
import com.example.projectmanagementbe.api.mappers.project.AssignmentMapper;
import com.example.projectmanagementbe.api.repositories.project.AssignmentRepository;
import com.example.projectmanagementbe.api.repositories.project.TaskRepository;
import com.example.projectmanagementbe.api.services.project.IAssignmentService;
import com.example.projectmanagementbe.auth.models.User;
import com.example.projectmanagementbe.auth.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService implements IAssignmentService {

  private final AssignmentRepository assignmentRepository;
  private final AssignmentMapper assignmentMapper;
  private final TaskRepository taskRepository;
  private final UserRepository userRepository;
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<AssignmentResponse> findAll() {
    return assignmentRepository.findAll().stream().map(assignmentMapper::toAssignmentResponse).toList();
  }

  @Override
  public AssignmentResponse create(AssignmentRequest assignmentRequest) {
//    Assignment assignment = assignmentMapper.toAssignmentEntity(assignmentRequest);
//    assignment.setAssigner(userRepository.findById(assignmentRequest.getAssignerId()).orElseThrow(() -> new RuntimeException("Assigner not found")));
//    if (assignmentRequest.getReceiverId() != null) {
//      assignment.setReceiver(userRepository.findById(assignmentRequest.getReceiverId()).orElseThrow(() -> new RuntimeException("Receiver not found")));
//    }
//
//    return assignmentMapper.toAssignmentResponse(assignmentRepository.save(assignment));

    Assignment assignment = new Assignment();

    User assigner = userRepository.findById(assignmentRequest.getAssignerId())
            .orElseThrow(() -> new RuntimeException("Assigner not found"));
    assignment.setAssigner(assigner);

    if (assignmentRequest.getReceiverId() != null) {
      User receiver = userRepository.findById(assignmentRequest.getReceiverId())
              .orElseThrow(() -> new RuntimeException("Receiver not found"));
      assignment.setReceiver(receiver);
    }

    Task task = taskRepository.findById(assignmentRequest.getTaskId())
            .orElseThrow(() -> new RuntimeException("Task not found"));
    assignment.setTask(task);

    assignment.setTitle(assignmentRequest.getTitle());
    assignment.setDescription(assignmentRequest.getDescription());
    assignment.setAssignmentOrder(assignmentRequest.getAssignmentOrder());
    assignment.setStatus_type(assignmentRequest.getStatus_type());
    assignment.setStart_date(assignmentRequest.getStart_date());
    assignment.setEnd_date(assignmentRequest.getEnd_date());

    return assignmentMapper.toAssignmentResponse(assignmentRepository.save(assignment));
  }

  @Override
  public void update(String id, AssignmentRequest assignmentRequest) {
    if (assignmentRequest.getOldAssignmentOrder() != null) {
      Assignment assignmentEntity = assignmentRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Assignment not found"));

      if (assignmentRequest.getOldTaskId() != null &&
              !assignmentRequest.getTaskId().equals(assignmentRequest.getOldTaskId())) {
        // Move to other task
        List<Assignment> assignmentsInNewTask = assignmentRepository.findByTask_Id(assignmentRequest.getTaskId());
        List<Assignment> assignmentsInOldTask = assignmentRepository.findByTask_Id(assignmentRequest.getOldTaskId());
        Task task = taskRepository.findById(assignmentRequest.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        for (Assignment assignment : assignmentsInOldTask) {
          if (assignment.getAssignmentOrder() > assignmentRequest.getOldAssignmentOrder()) {
            assignment.setAssignmentOrder(assignment.getAssignmentOrder() - 1);
          }
        }

        if (assignmentRequest.getAssignmentOrder() > assignmentsInNewTask.size()) {
          // Move to final position
          assignmentEntity.setTask(task);
        } else {
          // Move to non-final position
          for (Assignment assignment : assignmentsInNewTask) {
            if (assignment.getAssignmentOrder() >= assignmentRequest.getAssignmentOrder()) {
              assignment.setAssignmentOrder(assignment.getAssignmentOrder() + 1);
            }
          }
          assignmentEntity.setTask(task);
        }

        // Cập nhật start_date và end_date
        assignmentEntity.setStart_date(assignmentRequest.getStart_date());
        assignmentEntity.setEnd_date(assignmentRequest.getEnd_date());

        assignmentRepository.save(assignmentEntity);
        assignmentsInNewTask.add(assignmentEntity);
        assignmentRepository.saveAll(assignmentsInNewTask);
      } else {
        // Move to same task
        List<Assignment> assignmentsInTask = assignmentRepository.findByTask_Id(assignmentRequest.getTaskId());
        int oldAssignmentOrder = assignmentRequest.getOldAssignmentOrder();
        int newAssignmentOrder = assignmentRequest.getAssignmentOrder();

        if (oldAssignmentOrder > newAssignmentOrder) {
          // Move up
          for (Assignment assignment : assignmentsInTask) {
            if (assignment.getId().equals(id)) {
              assignment.setAssignmentOrder(newAssignmentOrder);
              assignment.setStart_date(assignmentRequest.getStart_date()); // Cập nhật start_date
              assignment.setEnd_date(assignmentRequest.getEnd_date());     // Cập nhật end_date
            } else if (assignment.getAssignmentOrder() >= newAssignmentOrder &&
                    assignment.getAssignmentOrder() < oldAssignmentOrder) {
              assignment.setAssignmentOrder(assignment.getAssignmentOrder() + 1);
            }
          }
        } else {
          // Move down
          for (Assignment assignment : assignmentsInTask) {
            if (assignment.getId().equals(id)) {
              assignment.setAssignmentOrder(newAssignmentOrder);
              assignment.setStart_date(assignmentRequest.getStart_date()); // Cập nhật start_date
              assignment.setEnd_date(assignmentRequest.getEnd_date());     // Cập nhật end_date
            } else if (assignment.getAssignmentOrder() <= newAssignmentOrder &&
                    assignment.getAssignmentOrder() > oldAssignmentOrder) {
              assignment.setAssignmentOrder(assignment.getAssignmentOrder() - 1);
            }
          }
        }

        assignmentRepository.saveAll(assignmentsInTask);
      }
    } else {
      Assignment assignment = assignmentRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Assignment not found"));

      User assigner = userRepository.findById(assignmentRequest.getAssignerId())
              .orElseThrow(() -> new RuntimeException("Assigner not found"));
      assignment.setAssigner(assigner);

      if (assignmentRequest.getReceiverId() != null) {
        User receiver = userRepository.findById(assignmentRequest.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        assignment.setReceiver(receiver);
      }

      Task task = taskRepository.findById(assignmentRequest.getTaskId())
              .orElseThrow(() -> new RuntimeException("Task not found"));
      assignment.setTask(task);

      assignment.setTitle(assignmentRequest.getTitle());
      assignment.setDescription(assignmentRequest.getDescription());
      assignment.setAssignmentOrder(assignmentRequest.getAssignmentOrder());
      if (assignmentRequest.getStatus_type() != null) {
        assignment.setStatus_type(assignmentRequest.getStatus_type());
      }
      assignment.setStart_date(assignmentRequest.getStart_date());
      assignment.setEnd_date(assignmentRequest.getEnd_date());

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