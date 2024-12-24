package com.example.projectmanagementbe.services.impls;

import com.example.projectmanagementbe.configs.JwtService;
import com.example.projectmanagementbe.dto.requests.auths.AuthRequest;
import com.example.projectmanagementbe.dto.requests.auths.RegisterRequest;
import com.example.projectmanagementbe.dto.responses.auths.AuthResponse;
import com.example.projectmanagementbe.entities.TokenEntity;
import com.example.projectmanagementbe.entities.UserEntity;
import com.example.projectmanagementbe.enums.TokenType;
import com.example.projectmanagementbe.mappers.RoleMapper;
import com.example.projectmanagementbe.mappers.UserMapper;
import com.example.projectmanagementbe.repositories.TokenRepository;
import com.example.projectmanagementbe.repositories.UserRepository;
import com.example.projectmanagementbe.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final RoleServiceImpl roleService;
  private final PasswordEncoder passwordEncoder;
  private final RoleMapper roleMapper;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public AuthResponse register(RegisterRequest registerRequest) {
    UserEntity user = UserEntity.builder()
        .name(registerRequest.getName())
        .email(registerRequest.getEmail())
        .username(registerRequest.getUsername())
        .password(passwordEncoder.encode(registerRequest.getPassword()))
        .phoneNumber(registerRequest.getPhoneNumber())
        .role(roleMapper.toRoleEntity(roleService.findById(registerRequest.getRoleId())))
        .build();

    UserEntity savedUser = userRepository.save(user);
    String jwtToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    saveUserToken(savedUser, jwtToken);

    return AuthResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  @Override
  public AuthResponse authenticate(AuthRequest authRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authRequest.getUsername(),
            authRequest.getPassword()
        )
    );

    UserEntity user = userRepository.findByUsername(authRequest.getUsername())
        .orElseThrow();
    String jwtToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);

    return AuthResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  private void saveUserToken(UserEntity savedUser, String jwtToken) {
    TokenEntity token = TokenEntity.builder()
        .user(savedUser)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();

    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(UserEntity userEntity) {
    List<TokenEntity> validUserTokens = tokenRepository.findAllValidTokensByUser(userEntity.getId());

    if (validUserTokens.isEmpty()) {
      return;
    }

    validUserTokens.forEach(tokenEntity -> {
      tokenEntity.setExpired(true);
      tokenEntity.setRevoked(true);
    });

    tokenRepository.saveAll(validUserTokens);
  }

  @Override
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String username;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }

    refreshToken = authHeader.substring(7);
    username = jwtService.extractUsername(refreshToken);

    if (username != null) {
      UserEntity userEntity = userRepository.findByUsername(username)
          .orElseThrow();

      if (jwtService.isTokenValid(refreshToken, userEntity)) {
        String accessToken = jwtService.generateToken(userEntity);

        revokeAllUserTokens(userEntity);
        saveUserToken(userEntity, accessToken);

        AuthResponse authResponse = AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();

        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
