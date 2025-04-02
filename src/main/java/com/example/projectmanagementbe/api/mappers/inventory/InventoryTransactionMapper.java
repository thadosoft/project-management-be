package com.example.projectmanagementbe.api.mappers.inventory;

import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryTransactionRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryTransactionRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryTransactionResponse;
import com.example.projectmanagementbe.api.models.iventory.InventoryTransaction;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryTransactionMapper {

  @Mapping(source = "item.id", target = "itemId")
  @Mapping(source = "item.name", target = "itemName")
  InventoryTransactionResponse mapInventoryTransactionResponse(InventoryTransaction inventoryTransaction);
  List<InventoryTransactionResponse> mapListInventoryTransaction(List<InventoryTransaction> inventoryTransaction);

  @Mapping(source = "itemId", target = "item.id")
  InventoryTransaction mapInventoryTransaction(InventoryTransactionRequest request);

  void update(UpdateInventoryTransactionRequest dto, @MappingTarget InventoryTransaction entity);
}
