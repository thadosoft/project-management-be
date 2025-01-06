package com.example.projectmanagementbe.controllers;

import com.example.projectmanagementbe.dto.requests.creates.RoleCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.RoleUpdateRequest;
import com.example.projectmanagementbe.dto.responses.RoleResponse;
import com.example.projectmanagementbe.services.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;

  @GetMapping
  public ResponseEntity<List<RoleResponse>> findAll() {
    return ResponseEntity.ok(roleService.findAll());
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody RoleCreateRequest roleCreateRequest) {
    roleService.create(roleCreateRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable String id, @RequestBody RoleUpdateRequest roleUpdateRequest) {
    roleService.update(id, roleUpdateRequest);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    roleService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<RoleResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(roleService.findById(id));
  }
}
