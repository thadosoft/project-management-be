package com.example.projectmanagementbe.api.services.inventory;

import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryItemResponse;
import java.util.List;

public interface IInventoryItemService {
  List<InventoryItemResponse> findAll();

  void create(InventoryItemRequest moduleRequest);

  void update(Long id, UpdateInventoryItemRequest inventoryCategoryRequest);

  void delete(Long id);

  InventoryItemResponse findById(Long id);
}
