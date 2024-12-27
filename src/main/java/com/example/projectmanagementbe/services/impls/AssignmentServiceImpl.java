package com.example.projectmanagementbe.services.impls;

import com.example.projectmanagementbe.dto.requests.creates.AssignmentCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.AssignmentUpdateRequest;
import com.example.projectmanagementbe.dto.responses.AssignmentResponse;
import com.example.projectmanagementbe.entities.AssignmentEntity;
import com.example.projectmanagementbe.mappers.AssignmentMapper;
import com.example.projectmanagementbe.repositories.AssignmentRepository;
import com.example.projectmanagementbe.services.AssignmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

  private final AssignmentRepository assignmentRepository;
  private final AssignmentMapper assignmentMapper;

  @Override
  public List<AssignmentResponse> findAll() {
    return assignmentRepository.findAll().stream().map(assignmentMapper::toAssignmentResponse).toList();
  }

  @Override
  public void create(AssignmentCreateRequest assignmentCreateRequest) {
    assignmentRepository.save(assignmentMapper.toAssignmentEntity(assignmentCreateRequest));
  }

  @Override
  public void update(Long id, AssignmentUpdateRequest assignmentUpdateRequest) {
    AssignmentEntity assignmentEntity = assignmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found"));

    assignmentMapper.toAssignmentEntity(assignmentUpdateRequest, assignmentEntity);

    assignmentRepository.save(assignmentEntity);
  }

  @Override
  public void delete(Long id) {
    if (assignmentRepository.existsById(id)) {
      assignmentRepository.deleteById(id);
    } else {
      throw new RuntimeException("Assignment not found");
    }
  }

  @Override
  public AssignmentResponse findById(Long id) {
    return assignmentMapper.toAssignmentResponse(assignmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found")));
  }

  @Override
  public List<AssignmentResponse> findByProjectId(Long id) {
    return assignmentMapper.toAssignmentResponses(assignmentRepository.findByTask_Project_Id(id));
  }
}
