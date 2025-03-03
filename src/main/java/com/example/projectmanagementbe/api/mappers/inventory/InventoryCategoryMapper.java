package com.example.projectmanagementbe.api.mappers.inventory;

import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryCategoryRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryCategoryRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryCategoryResponse;
import com.example.projectmanagementbe.api.models.iventory.InventoryCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryCategoryMapper {

  InventoryCategoryResponse mapInventoryCategoryResponse(InventoryCategory assignment);

  InventoryCategory mapInventoryCategory(InventoryCategoryRequest inventoryCategoryRequest);

  void update(UpdateInventoryCategoryRequest dto, @MappingTarget InventoryCategory entity);
}
