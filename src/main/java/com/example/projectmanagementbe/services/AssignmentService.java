package com.example.projectmanagementbe.services;

import com.example.projectmanagementbe.dto.requests.creates.AssignmentCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.AssignmentUpdateRequest;
import com.example.projectmanagementbe.dto.responses.AssignmentResponse;
import java.util.List;

public interface AssignmentService {
  List<AssignmentResponse> findAll();

  void create(AssignmentCreateRequest assignmentCreateRequest);

  void update(Long id, AssignmentUpdateRequest AssignmentUpdateRequest);

  void delete(Long id);

  AssignmentResponse findById(Long id);

  List<AssignmentResponse> findByProjectId(Long id);
}
