package com.example.projectmanagementbe.mappers;

import com.example.projectmanagementbe.dto.requests.creates.TaskCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.TaskUpdateRequest;
import com.example.projectmanagementbe.dto.responses.RoleResponse;
import com.example.projectmanagementbe.dto.responses.TaskResponse;
import com.example.projectmanagementbe.entities.RoleEntity;
import com.example.projectmanagementbe.entities.TaskEntity;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TaskMapper {

  TaskResponse toTaskResponse(TaskEntity taskEntity);

  List<TaskResponse> toTaskResponses(List<TaskEntity> taskEntities);

  @Mapping(source = "projectId", target = "project.id")
  TaskEntity toTaskEntity(TaskCreateRequest taskCreateRequest);

  TaskEntity toTaskEntity(TaskResponse taskResponse);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toTaskEntity(TaskUpdateRequest taskUpdateRequest, @MappingTarget TaskEntity taskEntity);

  String toRoleName(RoleEntity roleEntity);

  RoleEntity toRoleEntity(String value);
}
