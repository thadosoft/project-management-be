package com.example.projectmanagementbe.api.mappers.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.TaskRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.TaskResponse;
import com.example.projectmanagementbe.api.models.project.Task;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TaskMapper {

  TaskResponse toTaskResponse(Task task);

  List<TaskResponse> toTaskResponses(List<Task> taskEntities);

  @Mapping(source = "projectId", target = "project.id")
  Task toTaskEntity(TaskRequest taskRequest);

  Task toTaskEntity(TaskResponse taskResponse);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toTaskEntity(TaskRequest taskRequest, @MappingTarget Task task);
}
