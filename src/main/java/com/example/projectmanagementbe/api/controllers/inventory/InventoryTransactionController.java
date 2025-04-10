package com.example.projectmanagementbe.api.controllers.inventory;

import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryTransactionRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Search.SearchMaterialRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryTransactionRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryItemResponse;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryTransactionResponse;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.SearchInventoryTransactionRequest;
import com.example.projectmanagementbe.api.services.inventory.IInventoryTransactionService;
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
@RequestMapping("/api/v1/inventory/transactions")
@RequiredArgsConstructor
public class InventoryTransactionController {

  private final IInventoryTransactionService itemService;

  @GetMapping
  public ResponseEntity<List<InventoryTransactionResponse>> findAll() {
    return ResponseEntity.ok(itemService.findAll());
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody InventoryTransactionRequest request) {
    itemService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public void update(@PathVariable Long id, @RequestBody UpdateInventoryTransactionRequest request) {
    itemService.update(id, request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    itemService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<InventoryTransactionResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(itemService.findById(id));
  }

  @PostMapping("/search")
  public Page<InventoryTransactionResponse> search(
      @RequestBody SearchInventoryTransactionRequest request, Pageable pageable) {
    return itemService.searchByParams(request, pageable);
  }

}
