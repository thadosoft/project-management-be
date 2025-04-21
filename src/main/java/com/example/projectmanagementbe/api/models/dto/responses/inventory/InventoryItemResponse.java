package com.example.projectmanagementbe.api.models.dto.responses.inventory;

import com.example.projectmanagementbe.api.models.iventory.InventoryCategory;
import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
import com.example.projectmanagementbe.api.models.ReferenceFileV2;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryItemResponse {

  private Long id;
  private String name;
  private String sku;
  private InventoryCategory inventoryCategory;
  private String unit;
  private Integer quantityInStock;
  private Integer reorderLevel;
  private String location;
  private BigDecimal purchasePrice;
  private BigDecimal sellingPrice;
  private String status;
  private List<ReferenceFileV2> images;

  public static InventoryItemResponse fromEntity(InventoryItem item) {
    InventoryItemResponse response = new InventoryItemResponse();
    response.id = item.getId();
    response.name = item.getName();
    response.sku = item.getSku();
    response.inventoryCategory = item.getInventoryCategory();
    response.unit = item.getUnit();
    response.quantityInStock = item.getQuantityInStock();
    response.reorderLevel = item.getReorderLevel();
    response.location = item.getLocation();
    response.purchasePrice = item.getPurchasePrice();
    response.sellingPrice = item.getSellingPrice();
    response.status = item.getStatus();
    response.images = item.getImages() != null ? item.getImages() : new ArrayList<>();
    return response;
  }

  public static List<InventoryItemResponse> fromEntities(List<InventoryItem> items) {
    return items.stream()
            .map(InventoryItemResponse::fromEntity)
            .collect(Collectors.toList());
  }
}