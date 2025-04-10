package com.example.projectmanagementbe.api.services.inventory;

import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryTransactionRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Search.SearchMaterialRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryTransactionRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryItemResponse;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryTransactionResponse;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.SearchInventoryTransactionRequest;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IInventoryTransactionService {

  List<InventoryTransactionResponse> findAll();

  void create(InventoryTransactionRequest request);

  void update(Long id, UpdateInventoryTransactionRequest request);

  void delete(Long id);

  InventoryTransactionResponse findById(Long id);

  Page<InventoryTransactionResponse> searchByParams(SearchInventoryTransactionRequest request, Pageable pageable);
}
