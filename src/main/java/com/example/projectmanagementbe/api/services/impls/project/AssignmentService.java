package com.example.projectmanagementbe.api.services.impls.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.AssignmentRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.AssignmentResponse;
import com.example.projectmanagementbe.api.models.project.Assignment;
import com.example.projectmanagementbe.api.models.project.Task;
import com.example.projectmanagementbe.api.repositories.project.AssignmentRepository;
import com.example.projectmanagementbe.api.repositories.project.TaskRepository;
import com.example.projectmanagementbe.api.services.mail.MailService;
import com.example.projectmanagementbe.api.services.project.IAssignmentService;
import com.example.projectmanagementbe.auth.models.User;
import com.example.projectmanagementbe.auth.repositories.UserRepository;
import com.example.projectmanagementbe.api.mappers.project.AssignmentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignmentService implements IAssignmentService {

  private final AssignmentRepository assignmentRepository;
  private final AssignmentMapper assignmentMapper;
  private final TaskRepository taskRepository;
  private final UserRepository userRepository;
  private final MailService mailService;

  @Override
  public List<AssignmentResponse> findAll() {
    return assignmentMapper.toAssignmentResponses(assignmentRepository.findAll());
  }

  @Override
  public AssignmentResponse create(AssignmentRequest request) {
    Assignment assignment = new Assignment();
    assignment.setTitle(request.getTitle());
    assignment.setDescription(request.getDescription());
    assignment.setAssignmentOrder(request.getAssignmentOrder());
    assignment.setStatus_type(request.getStatus_type());

    if (request.getStart_date() != null) {
      assignment.setStart_date(request.getStart_date());
    }

    if (request.getEnd_date() != null) {
      assignment.setEnd_date(request.getEnd_date());
    }

    // Gán task
    Task task = taskRepository.findById(request.getTaskId())
            .orElseThrow(() -> new RuntimeException("Task not found"));
    assignment.setTask(task);

    // Gán assigner nếu có
    if (request.getAssignerId() != null && !request.getAssignerId().isEmpty()) {
      userRepository.findById(request.getAssignerId()).ifPresentOrElse(
              assignment::setAssigner,
              () -> log.warn("Assigner not found: {}", request.getAssignerId())
      );
    }

    // Gán receiver nếu có
    if (request.getReceiverId() != null && !request.getReceiverId().isEmpty()) {
      userRepository.findById(request.getReceiverId()).ifPresentOrElse(
              receiver -> {
                assignment.setReceiver(receiver);
                // Gửi email thông báo
                try {
                  mailService.sendAssignmentNotification(receiver.getEmail(), assignment.getTitle(), task.getStatus(), assignment.getAssigner().getName());
                } catch (Exception e) {
                  log.error("Failed to send assignment email to {}", receiver.getEmail(), e);
                }
              },
              () -> log.warn("Receiver not found: {}", request.getReceiverId())
      );
    }

    Assignment saved = assignmentRepository.save(assignment);
    return assignmentMapper.toAssignmentResponse(saved);
  }

  @Override
  public void update(String id, AssignmentRequest request) {
    Assignment assignment = assignmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Assignment not found"));

    assignment.setTitle(request.getTitle());
    assignment.setDescription(request.getDescription());
    assignment.setAssignmentOrder(request.getAssignmentOrder());
    assignment.setStatus_type(request.getStatus_type());
    assignment.setStart_date(request.getStart_date());
    assignment.setEnd_date(request.getEnd_date());

    // Task
    if (request.getTaskId() != null && !request.getTaskId().isEmpty()) {
      Task task = taskRepository.findById(request.getTaskId())
              .orElseThrow(() -> new RuntimeException("Task not found"));
      assignment.setTask(task);
    }

    //Assignment
    if (assignment.getAssignmentOrder() < 1) {
      assignment.setAssignmentOrder(1);
    }

    // Assigner
    if (request.getAssignerId() != null && !request.getAssignerId().isEmpty()) {
      userRepository.findById(request.getAssignerId()).ifPresent(assignment::setAssigner);
    }

    // Receiver + gửi mail
    if (request.getReceiverId() != null && !request.getReceiverId().isEmpty()) {
      userRepository.findById(request.getReceiverId()).ifPresentOrElse(
              receiver -> {
                assignment.setReceiver(receiver);
                try {
                  mailService.sendAssignmentNotification(
                          receiver.getEmail(),
                          assignment.getTitle(),
                          assignment.getTask() != null ? assignment.getTask().getStatus() : "(Chưa rõ)",
                          assignment.getAssigner() != null ? assignment.getAssigner().getName() : "Không xác định"
                  );
                } catch (Exception e) {
                  log.error("Gửi email thất bại đến {}", receiver.getEmail(), e);
                }
              },
              () -> log.warn("Receiver not found: {}", request.getReceiverId())
      );
    }

    assignmentRepository.save(assignment);
  }

  @Override
  public void delete(String id) {
    assignmentRepository.deleteById(id);
  }

  @Override
  public AssignmentResponse findById(String id) {
    return assignmentMapper.toAssignmentResponse(
            assignmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Assignment not found"))
    );
  }

  @Override
  public List<AssignmentResponse> findByProjectId(String projectId) {
    return assignmentMapper.toAssignmentResponses(
            assignmentRepository.findByTask_Project_Id(projectId)
    );
  }
}
