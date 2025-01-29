package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.models.dto.requests.UserRequest;
import com.example.projectmanagementbe.api.models.dto.responses.UserResponse;
import com.example.projectmanagementbe.auth.models.User;
import com.example.projectmanagementbe.exception.ApiRequestException;
import com.example.projectmanagementbe.exception.ErrorCode;
import com.example.projectmanagementbe.api.mappers.UserMapper;
import com.example.projectmanagementbe.auth.repositories.UserRepository;
import com.example.projectmanagementbe.api.services.IUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public List<UserResponse> findAll() {
    return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
  }

  @Override
  public void update(String id, UserRequest userRequest) {
    User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

    if (userRequest.getRole() != null) {
      user.setRole(userRequest.getRole());
    }

    userMapper.toUserEntity(userRequest, user);

    userRepository.save(user);
  }

  @Override
  public void delete(String id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
    } else {
      throw new RuntimeException("User not found");
    }
  }

  @Override
  public UserResponse findById(String id) {
    return userMapper.toUserResponse(userRepository.findById(id)
        .orElseThrow(() -> new ApiRequestException(ErrorCode.USER_NOT_FOUND)));
  }
}
