package com.example.projectmanagementbe.api.mappers.inventory;

import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryItemResponse;
import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryItemMapper {

  InventoryItemResponse mapInventoryItemResponse(InventoryItem inventoryItem);

  InventoryItem mapInventoryItem(InventoryItemRequest request);

  void update(UpdateInventoryItemRequest dto, @MappingTarget InventoryItem entity);
}
