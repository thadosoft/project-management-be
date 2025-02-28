package com.example.projectmanagementbe.api.services.inventory;

import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryTransactionRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryTransactionRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryTransactionResponse;
import java.util.List;

public interface IInventoryTransactionService {

  List<InventoryTransactionResponse> findAll();

  void create(InventoryTransactionRequest request);

  void update(Long id, UpdateInventoryTransactionRequest request);

  void delete(Long id);

  InventoryTransactionResponse findById(Long id);
}
