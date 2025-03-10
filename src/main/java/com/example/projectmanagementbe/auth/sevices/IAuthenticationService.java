package com.example.projectmanagementbe.auth.sevices;

import com.example.projectmanagementbe.auth.models.dto.requests.AuthenticationRequest;
import com.example.projectmanagementbe.auth.models.dto.requests.RegisterRequest;
import com.example.projectmanagementbe.auth.models.dto.responses.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IAuthenticationService {

  public AuthenticationResponse register(RegisterRequest registerRequest);

  public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

  void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

  boolean isTokenValid(String token);
}
