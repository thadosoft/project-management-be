package com.example.projectmanagementbe.api.mappers.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.AssignmentRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.AssignmentResponse;
import com.example.projectmanagementbe.api.models.project.Assignment;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
  @Mapping(source = "status_type", target = "status_type")
  @Mapping(source = "start_date", target = "start_date")
  @Mapping(source = "end_date", target = "end_date")
  AssignmentResponse toAssignmentResponse(Assignment assignment);

  List<AssignmentResponse> toAssignmentResponses(List<Assignment> assignmentEntities);

  @Mapping(source = "assignerId", target = "assigner.id")
  @Mapping(source = "receiverId", target = "receiver.id")
  @Mapping(source = "taskId", target = "task.id")
  @Mapping(source = "status_type", target = "status_type")
  @Mapping(source = "start_date", target = "start_date")
  @Mapping(source = "end_date", target = "end_date")
  Assignment toAssignmentEntity(AssignmentRequest assignmentRequest);

  Assignment toAssignmentEntity(AssignmentResponse assignmentResponse);

  @Mapping(source = "assignerId", target = "assigner.id")
  @Mapping(source = "receiverId", target = "receiver.id")
  @Mapping(source = "taskId", target = "task.id")
  @Mapping(source = "status_type", target = "status_type")
  @Mapping(source = "start_date", target = "start_date")
  @Mapping(source = "end_date", target = "end_date")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toAssignmentEntity(AssignmentRequest assignmentRequest, @MappingTarget Assignment assignment);
}