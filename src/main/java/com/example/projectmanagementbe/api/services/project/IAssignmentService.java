package com.example.projectmanagementbe.api.services.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.AssignmentRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.AssignmentResponse;
import java.util.List;

public interface IAssignmentService {
  List<AssignmentResponse> findAll();

  AssignmentResponse create(AssignmentRequest assignmentRequest);

  void update(String id, AssignmentRequest assignmentRequest); // Đổi từ void thành AssignmentResponse
  void delete(String id);

  AssignmentResponse findById(String id);

  List<AssignmentResponse> findByProjectId(String id);
}
