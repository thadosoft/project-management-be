package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.dto.requests.AssignmentRequest;
import com.example.projectmanagementbe.api.models.dto.responses.AssignmentResponse;
import com.example.projectmanagementbe.api.models.Assignment;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

  AssignmentResponse toAssignmentResponse(Assignment assignment);

  List<AssignmentResponse> toAssignmentResponses(List<Assignment> assignmentEntities);

  @Mapping(source = "assignerId", target = "assigner.id")
  @Mapping(source = "receiverId", target = "receiver.id")
  @Mapping(source = "taskId", target = "task.id")
  Assignment toAssignmentEntity(AssignmentRequest assignmentRequest);

  Assignment toAssignmentEntity(AssignmentResponse assignmentResponse);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toAssignmentEntity(AssignmentRequest assignmentRequest, @MappingTarget Assignment assignment);
}
