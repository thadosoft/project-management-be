package com.example.projectmanagementbe.api.mappers.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.ProjectRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.ProjectResponse;
import com.example.projectmanagementbe.api.models.project.Project;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

  ProjectResponse toProjectResponse(Project project);

  @Mapping(source = "userId", target = "user.id")
  Project toProjectEntity(ProjectRequest projectRequest);

  Project toProjectEntity(ProjectResponse projectResponse);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toProjectEntity(ProjectRequest projectRequest, @MappingTarget Project project);
}
