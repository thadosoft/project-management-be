package com.example.projectmanagementbe.api.controllers.inventory;

import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Search.SearchMaterialRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Search.SearchReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryItemResponse;
import com.example.projectmanagementbe.api.models.dto.responses.referenceProfile.ReferenceProfileResponse;
import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
import com.example.projectmanagementbe.api.services.inventory.IInventoryItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/v1/inventory/materials")
@RequiredArgsConstructor
public class InventoryItemController {

  private final IInventoryItemService itemService;

  @GetMapping
  public ResponseEntity<List<InventoryItemResponse>> findAll() {
    return ResponseEntity.ok(itemService.findAll());
  }

  @PostMapping("/search")
  public Page<InventoryItemResponse> search(@RequestBody SearchMaterialRequest request, Pageable pageable) {
    return itemService.searchByParams(request, pageable);
  }

  @PostMapping
  public ResponseEntity<InventoryItemResponse> create(@RequestBody InventoryItemRequest request) {
    InventoryItem item = itemService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(InventoryItemResponse.fromEntity(item));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateInventoryItemRequest request) {
    itemService.update(id, request);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    itemService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<InventoryItemResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(itemService.findById(id));
  }
}