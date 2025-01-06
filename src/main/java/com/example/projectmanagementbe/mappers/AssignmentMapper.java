package com.example.projectmanagementbe.mappers;

import com.example.projectmanagementbe.dto.requests.creates.AssignmentCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.AssignmentUpdateRequest;
import com.example.projectmanagementbe.dto.responses.AssignmentResponse;
import com.example.projectmanagementbe.dto.responses.MediaResponse;
import com.example.projectmanagementbe.entities.AssignmentEntity;
import com.example.projectmanagementbe.entities.MediaEntity;
import com.example.projectmanagementbe.entities.RoleEntity;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

  @Mapping(source = "assigner.role.name", target = "assigner.role")
  @Mapping(source = "receiver.role.name", target = "receiver.role")
  AssignmentResponse toAssignmentResponse(AssignmentEntity assignmentEntity);

  List<AssignmentResponse> toAssignmentResponses(List<AssignmentEntity> assignmentEntities);

  @Mapping(source = "assignerId", target = "assigner.id")
  @Mapping(source = "receiverId", target = "receiver.id")
  @Mapping(source = "taskId", target = "task.id")
  AssignmentEntity toAssignmentEntity(AssignmentCreateRequest assignmentCreateRequest);

  @Mapping(source = "receiver.role", target = "receiver.role.name")
  @Mapping(source = "assigner.role", target = "assigner.role.name")
  AssignmentEntity toAssignmentEntity(AssignmentResponse assignmentResponse);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toAssignmentEntity(AssignmentUpdateRequest assignmentUpdateRequest, @MappingTarget AssignmentEntity assignmentEntity);

  String toRoleName(RoleEntity roleEntity);

  RoleEntity toRoleEntity(String value);
}
