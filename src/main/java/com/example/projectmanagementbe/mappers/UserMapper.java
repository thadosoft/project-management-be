package com.example.projectmanagementbe.mappers;

import com.example.projectmanagementbe.dto.requests.updates.UserUpdateRequest;
import com.example.projectmanagementbe.dto.responses.UserResponse;
import com.example.projectmanagementbe.entities.UserEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

  //  @Mapping(source = "role.name", target = "role")
  @Mapping(target = "role", expression = "java(userEntity.getRole() != null ? userEntity.getRole().getName() : null)")
  UserResponse toUserResponse(UserEntity userEntity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toUserEntity(UserUpdateRequest userUpdateRequest, @MappingTarget UserEntity userEntity);
}
