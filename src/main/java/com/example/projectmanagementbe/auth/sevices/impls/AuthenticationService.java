package com.example.projectmanagementbe.auth.sevices.impls;

import com.example.projectmanagementbe.auth.configs.security.JwtService;
import com.example.projectmanagementbe.auth.models.dto.requests.AuthenticationRequest;
import com.example.projectmanagementbe.auth.models.dto.requests.ForgotPasswordRequest;
import com.example.projectmanagementbe.auth.models.dto.requests.RegisterRequest;
import com.example.projectmanagementbe.auth.models.dto.requests.ResetPasswordRequest;
import com.example.projectmanagementbe.auth.models.dto.responses.AuthenticationResponse;
import com.example.projectmanagementbe.auth.enums.UserRole;
import com.example.projectmanagementbe.auth.models.Token;
import com.example.projectmanagementbe.auth.models.User;
import com.example.projectmanagementbe.auth.enums.TokenType;
import com.example.projectmanagementbe.api.repositories.TokenRepository;
import com.example.projectmanagementbe.auth.models.dto.responses.ForgotPasswordResponse;
import com.example.projectmanagementbe.auth.repositories.UserRepository;
import com.example.projectmanagementbe.auth.sevices.IAuthenticationService;
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
public class AuthenticationService implements IAuthenticationService {

  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public AuthenticationResponse register(RegisterRequest registerRequest) {
    User user = User.builder()
        .name(registerRequest.getName())
        .email(registerRequest.getEmail())
        .username(registerRequest.getUsername())
        .password(passwordEncoder.encode(registerRequest.getPassword()))
        .phoneNumber(registerRequest.getPhoneNumber())
        .role(UserRole.valueOf(registerRequest.getRole()))
        .build();

    User savedUser = userRepository.save(user);
    String jwtToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    saveUserToken(savedUser, jwtToken);

    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .id(savedUser.getId())
        .build();
  }

  @Override
  public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authenticationRequest.getUsername(),
            authenticationRequest.getPassword()
        )
    );

    User user = userRepository.findByUsername(authenticationRequest.getUsername())
        .orElseThrow();
    String jwtToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);

    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .id(user.getId())
        .build();
  }

  private void saveUserToken(User savedUser, String jwtToken) {
    Token token = Token.builder()
        .user(savedUser)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();

    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());

    if (validUserTokens.isEmpty()) {
      return;
    }

    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
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
      User user = userRepository.findByUsername(username)
          .orElseThrow();

      if (jwtService.isTokenValid(refreshToken, user)) {
        String accessToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();

        new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponse);
      }
    }
  }

  @Override
  public boolean isTokenValid(String token) {
    return tokenRepository.existsByToken(token);
  }

  @Override
  public ForgotPasswordResponse verifyUsername(ForgotPasswordRequest request) {
    boolean usernameExists = userRepository.findByUsername(request.getUsername()).isPresent();
    return ForgotPasswordResponse.builder()
            .usernameExists(usernameExists)
            .message(usernameExists ? "Username verified successfully" : "Username does not exist")
            .build();
  }

  @Override
  public void resetPassword(ResetPasswordRequest request) {
    if (!request.getPassword().equals(request.getConfirmPassword())) {
      throw new IllegalArgumentException("Passwords do not match");
    }

    User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new IllegalArgumentException("Username does not exist"));

    user.setPassword(passwordEncoder.encode(request.getPassword()));
    userRepository.save(user);

    // Revoke all existing tokens to force re-authentication
    revokeAllUserTokens(user);
  }
}
