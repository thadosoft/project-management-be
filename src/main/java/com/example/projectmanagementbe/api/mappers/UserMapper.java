package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.dto.requests.UserRequest;
import com.example.projectmanagementbe.api.models.dto.responses.UserResponse;
import com.example.projectmanagementbe.auth.models.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserResponse toUserResponse(User user);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toUserEntity(UserRequest userUpdateRequest, @MappingTarget User user);
}
