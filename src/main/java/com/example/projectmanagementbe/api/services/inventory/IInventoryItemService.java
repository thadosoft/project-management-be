package com.example.projectmanagementbe.api.services.inventory;

import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Search.SearchMaterialRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryItemResponse;
import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IInventoryItemService {

  List<InventoryItemResponse> findAll();

  Page<InventoryItemResponse> searchByParams(SearchMaterialRequest request, Pageable pageable);

  InventoryItem create(InventoryItemRequest request);

  void update(Long id, UpdateInventoryItemRequest request);

  void delete(Long id);

  InventoryItemResponse findById(Long id);
}