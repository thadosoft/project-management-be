package com.example.projectmanagementbe.api.mappers.inventory;

import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryTransactionRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryTransactionRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryTransactionResponse;
import com.example.projectmanagementbe.api.models.iventory.InventoryTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryTransactionMapper {

  InventoryTransactionResponse mapInventoryTransaction(InventoryTransaction inventoryTransaction);

  InventoryTransaction mapInventoryTransaction(InventoryTransactionRequest request);

  void update(UpdateInventoryTransactionRequest dto, @MappingTarget InventoryTransaction entity);
}
