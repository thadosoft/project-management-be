package com.example.projectmanagementbe.api.services.inventory;

import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryCategoryRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryCategoryRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryCategoryResponse;
import java.util.List;

public interface IInventoryCategoryService {

  List<InventoryCategoryResponse> findAll();

  void create(InventoryCategoryRequest moduleRequest);

  void update(Long id, UpdateInventoryCategoryRequest moduleRequest);

  void delete(Long id);

  InventoryCategoryResponse findById(Long id);
}
