package com.example.projectmanagementbe.services;

import com.example.projectmanagementbe.dto.requests.auths.AuthRequest;
import com.example.projectmanagementbe.dto.requests.auths.RegisterRequest;
import com.example.projectmanagementbe.dto.responses.auths.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthService {

  public AuthResponse register(RegisterRequest registerRequest);

  public AuthResponse authenticate(AuthRequest authRequest);

  void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
