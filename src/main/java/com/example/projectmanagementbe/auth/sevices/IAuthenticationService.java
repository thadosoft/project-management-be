package com.example.projectmanagementbe.auth.sevices;

import com.example.projectmanagementbe.auth.models.dto.requests.AuthenticationRequest;
import com.example.projectmanagementbe.auth.models.dto.requests.ForgotPasswordRequest;
import com.example.projectmanagementbe.auth.models.dto.requests.RegisterRequest;
import com.example.projectmanagementbe.auth.models.dto.requests.ResetPasswordRequest;
import com.example.projectmanagementbe.auth.models.dto.responses.AuthenticationResponse;
import com.example.projectmanagementbe.auth.models.dto.responses.ForgotPasswordResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IAuthenticationService {

  AuthenticationResponse register(RegisterRequest registerRequest);

  AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

  void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

  boolean isTokenValid(String token);

  ForgotPasswordResponse verifyUsername(ForgotPasswordRequest request);

  void resetPassword(ResetPasswordRequest request);
}