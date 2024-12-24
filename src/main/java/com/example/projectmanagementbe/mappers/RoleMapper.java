package com.example.projectmanagementbe.mappers;

import com.example.projectmanagementbe.dto.requests.creates.RoleCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.RoleUpdateRequest;
import com.example.projectmanagementbe.dto.responses.RoleResponse;
import com.example.projectmanagementbe.entities.RoleEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface RoleMapper {

  RoleResponse toRoleResponse(RoleEntity roleEntity);

  RoleEntity toRoleEntity(RoleCreateRequest roleCreateRequest);

  RoleEntity toRoleEntity(RoleResponse roleResponse);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toRoleEntity(RoleUpdateRequest roleUpdateRequest, @MappingTarget RoleEntity roleEntity);
}
