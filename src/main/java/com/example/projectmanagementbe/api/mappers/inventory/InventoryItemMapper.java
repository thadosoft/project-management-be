package com.example.projectmanagementbe.api.mappers.inventory;

import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryItemResponse;
import com.example.projectmanagementbe.api.models.iventory.InventoryCategory;
import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryItemMapper {

  InventoryItemResponse mapInventoryItemResponse(InventoryItem inventoryItem);

  @Mapping(source = "inventoryCategoryId", target = "inventoryCategory.id")
  InventoryItem mapInventoryItem(InventoryItemRequest request);

  InventoryCategory mapCategory(Long inventoryCategoryId);

  void update(UpdateInventoryItemRequest dto, @MappingTarget InventoryItem entity);
}
