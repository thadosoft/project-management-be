package com.example.projectmanagementbe.api.controllers.inventory;

import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryCategoryRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Search.SearchInventoryCategoryRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryCategoryRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryCategoryResponse;
import com.example.projectmanagementbe.api.services.inventory.IInventoryCategoryService;
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
@RequestMapping("/api/v1/inventory/categories")
@RequiredArgsConstructor
public class InventoryCategoryController {

  private final IInventoryCategoryService iInventoryCategoryService;

  @GetMapping
  public ResponseEntity<List<InventoryCategoryResponse>> findAll() {
    return ResponseEntity.ok(iInventoryCategoryService.findAll());
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody InventoryCategoryRequest moduleRequest) {
    iInventoryCategoryService.create(moduleRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateInventoryCategoryRequest moduleRequest) {
    iInventoryCategoryService.update(id, moduleRequest);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    iInventoryCategoryService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<InventoryCategoryResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(iInventoryCategoryService.findById(id));
  }
}
