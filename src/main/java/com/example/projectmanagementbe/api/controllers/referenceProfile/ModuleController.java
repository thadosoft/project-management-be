package com.example.projectmanagementbe.api.controllers.referenceProfile;

import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.ModuleRequest;
import com.example.projectmanagementbe.api.models.dto.responses.referenceProfile.ModuleResponse;
import com.example.projectmanagementbe.api.services.referenceProfile.IModuleService;
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
@RequestMapping("/api/v1/modules")
@RequiredArgsConstructor
public class ModuleController {

  private final IModuleService moduleService;

  @GetMapping
  public ResponseEntity<List<ModuleResponse>> findAll() {
    return ResponseEntity.ok(moduleService.findAll());
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody ModuleRequest moduleRequest) {
    moduleService.create(moduleRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ModuleRequest moduleRequest) {
    moduleService.update(id, moduleRequest);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    moduleService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ModuleResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(moduleService.findById(id));
  }
}
