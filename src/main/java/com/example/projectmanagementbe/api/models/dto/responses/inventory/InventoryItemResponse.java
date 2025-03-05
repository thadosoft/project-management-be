package com.example.projectmanagementbe.api.models.dto.responses.inventory;

import com.example.projectmanagementbe.api.models.iventory.InventoryCategory;
import java.math.BigDecimal;
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
}
