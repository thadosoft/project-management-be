package com.example.projectmanagementbe.services;

import com.example.projectmanagementbe.dto.requests.creates.AssignmentCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.AssignmentUpdateRequest;
import com.example.projectmanagementbe.dto.responses.AssignmentResponse;
import java.util.List;

public interface AssignmentService {
  List<AssignmentResponse> findAll();

  void create(AssignmentCreateRequest assignmentCreateRequest);

  void update(String id, AssignmentUpdateRequest AssignmentUpdateRequest);

  void delete(String id);

  AssignmentResponse findById(String id);

  List<AssignmentResponse> findByProjectId(String id);
}
