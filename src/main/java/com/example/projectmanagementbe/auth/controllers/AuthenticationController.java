package com.example.projectmanagementbe.auth.controllers;

import com.example.projectmanagementbe.auth.models.dto.requests.AuthenticationRequest;
import com.example.projectmanagementbe.auth.models.dto.requests.ForgotPasswordRequest;
import com.example.projectmanagementbe.auth.models.dto.requests.RegisterRequest;
import com.example.projectmanagementbe.auth.models.dto.requests.ResetPasswordRequest;
import com.example.projectmanagementbe.auth.models.dto.responses.AuthenticationResponse;
import com.example.projectmanagementbe.auth.models.dto.responses.ForgotPasswordResponse;
import com.example.projectmanagementbe.auth.sevices.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final IAuthenticationService IAuthenticationService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
    return ResponseEntity.ok(IAuthenticationService.register(registerRequest));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
    return ResponseEntity.ok(IAuthenticationService.authenticate(authenticationRequest));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    IAuthenticationService.refreshToken(request, response);
  }

  @PostMapping("/validate-token")
  public ResponseEntity<Boolean> isTokenValid(@RequestParam String token) {
    return ResponseEntity.ok(IAuthenticationService.isTokenValid(token));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<ForgotPasswordResponse> verifyUsername(@RequestBody ForgotPasswordRequest request) {
    return ResponseEntity.ok(IAuthenticationService.verifyUsername(request));
  }

  @PostMapping("/reset-password")
  public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
    IAuthenticationService.resetPassword(request);
    return ResponseEntity.ok().build();
  }
}