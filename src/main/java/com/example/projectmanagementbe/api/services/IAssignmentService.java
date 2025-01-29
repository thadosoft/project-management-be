package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.dto.requests.AssignmentRequest;
import com.example.projectmanagementbe.api.models.dto.responses.AssignmentResponse;
import java.util.List;

public interface IAssignmentService {
  List<AssignmentResponse> findAll();

  void create(AssignmentRequest assignmentRequest);

  void update(String id, AssignmentRequest AssignmentRequest);

  void delete(String id);

  AssignmentResponse findById(String id);

  List<AssignmentResponse> findByProjectId(String id);
}
