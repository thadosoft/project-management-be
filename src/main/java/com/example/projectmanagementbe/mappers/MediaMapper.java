package com.example.projectmanagementbe.mappers;

import com.example.projectmanagementbe.dto.requests.creates.MediaCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.MediaUpdateRequest;
import com.example.projectmanagementbe.dto.responses.MediaResponse;
import com.example.projectmanagementbe.entities.MediaEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MediaMapper {

  MediaResponse toMediaResponse(MediaEntity mediaEntity);

  MediaEntity toMediaEntity(MediaCreateRequest mediaCreateRequest);

  MediaEntity toMediaEntity(MediaResponse mediaResponse);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toMediaEntity(MediaUpdateRequest mediaUpdateRequest, @MappingTarget MediaEntity mediaEntity);
}
