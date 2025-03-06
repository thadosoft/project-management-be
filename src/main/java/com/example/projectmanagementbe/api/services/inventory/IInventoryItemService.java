package com.example.projectmanagementbe.api.services.inventory;

import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Search.SearchMaterialRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryItemResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IInventoryItemService {

  List<InventoryItemResponse> findAll();

  Page<InventoryItemResponse> searchByParams(SearchMaterialRequest request, Pageable pageable);

  void create(InventoryItemRequest moduleRequest);

  void update(Long id, UpdateInventoryItemRequest inventoryCategoryRequest);

  void delete(Long id);

  InventoryItemResponse findById(Long id);
}
