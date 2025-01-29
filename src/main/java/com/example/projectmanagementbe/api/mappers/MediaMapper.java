package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.dto.requests.MediaRequest;
import com.example.projectmanagementbe.api.models.dto.responses.MediaResponse;
import com.example.projectmanagementbe.api.models.Media;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MediaMapper {

  MediaResponse toMediaResponse(Media media);

  @Mapping(source = "assignmentId", target = "assignment.id")
  Media toMediaEntity(MediaRequest mediaRequest);

  Media toMediaEntity(MediaResponse mediaResponse);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toMediaEntity(MediaRequest mediaRequest, @MappingTarget Media media);
}
