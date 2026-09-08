package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.auth.models.User;
import com.example.projectmanagementbe.auth.repositories.UserRepository;
import com.example.projectmanagementbe.exception.ApiRequestException;
import com.example.projectmanagementbe.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/** Resolves the authenticated {@link User} from the security context. */
@Component
@RequiredArgsConstructor
public class CurrentUserProvider {

  private final UserRepository userRepository;

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || authentication.getName() == null) {
      throw new ApiRequestException(ErrorCode.UNAUTHORIZED);
    }
    return userRepository.findByUsername(authentication.getName())
        .orElseThrow(() -> new ApiRequestException(ErrorCode.USER_NOT_FOUND));
  }
}
