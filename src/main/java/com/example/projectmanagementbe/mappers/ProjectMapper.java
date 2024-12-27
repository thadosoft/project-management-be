package com.example.projectmanagementbe.mappers;

import com.example.projectmanagementbe.dto.requests.creates.ProjectCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.ProjectUpdateRequest;
import com.example.projectmanagementbe.dto.responses.ProjectResponse;
import com.example.projectmanagementbe.entities.RoleEntity;
import com.example.projectmanagementbe.entities.ProjectEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

  ProjectResponse toProjectResponse(ProjectEntity projectEntity);

  @Mapping(source = "userId", target = "user.id")
  ProjectEntity toProjectEntity(ProjectCreateRequest projectCreateRequest);

  ProjectEntity toProjectEntity(ProjectResponse projectResponse);

  String toRoleName(RoleEntity roleEntity);

  RoleEntity toRoleEntity(String value);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toProjectEntity(ProjectUpdateRequest projectUpdateRequest, @MappingTarget ProjectEntity projectEntity);
}
