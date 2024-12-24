package com.example.projectmanagementbe.services.impls;

import com.example.projectmanagementbe.dto.requests.updates.UserUpdateRequest;
import com.example.projectmanagementbe.dto.responses.UserResponse;
import com.example.projectmanagementbe.entities.RoleEntity;
import com.example.projectmanagementbe.entities.UserEntity;
import com.example.projectmanagementbe.exceptions.ApiRequestException;
import com.example.projectmanagementbe.exceptions.ErrorCode;
import com.example.projectmanagementbe.mappers.UserMapper;
import com.example.projectmanagementbe.repositories.RoleRepository;
import com.example.projectmanagementbe.repositories.UserRepository;
import com.example.projectmanagementbe.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserMapper userMapper;

  @Override
  public List<UserResponse> findAll() {
    return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
  }

  @Override
  public void update(Long id, UserUpdateRequest userUpdateRequest) {
    UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

    if (userUpdateRequest.getRoleId() != null) {
      RoleEntity roleEntity = roleRepository.findById(userUpdateRequest.getRoleId()).orElseThrow(() -> new RuntimeException("Role not found"));
      userEntity.setRole(roleEntity);
    }

    userMapper.toUserEntity(userUpdateRequest, userEntity);

    userRepository.save(userEntity);
  }

  @Override
  public void delete(Long id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
    } else {
      throw new RuntimeException("User not found");
    }
  }

  @Override
  public UserResponse findById(Long id) {
    return userMapper.toUserResponse(userRepository.findById(id)
        .orElseThrow(() -> new ApiRequestException(ErrorCode.USER_NOT_FOUND)));
  }
}
