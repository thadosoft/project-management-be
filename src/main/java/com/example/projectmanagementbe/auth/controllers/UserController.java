package com.example.projectmanagementbe.auth.controllers;

import com.example.projectmanagementbe.api.models.dto.requests.UserRequest;
import com.example.projectmanagementbe.api.models.dto.responses.UserResponse;
import com.example.projectmanagementbe.api.services.IUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class  UserController {

  private final IUserService IUserService;

  @GetMapping
  public ResponseEntity<List<UserResponse>> findAll() {
    return ResponseEntity.ok(IUserService.findAll());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable String id, @RequestBody UserRequest userRequest) {
    IUserService.update(id, userRequest);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    IUserService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(IUserService.findById(id));
  }
}
