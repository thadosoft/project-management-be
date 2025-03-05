package com.example.projectmanagementbe.api.models.dto.responses.inventory;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryItemResponse {

  private Long id;

  private String sku;

  private Long inventoryCategoryId;

  private String unit;

  private Integer quantityInStock;

  private Integer reorderLevel;

  private String location;

  private BigDecimal purchasePrice;

  private BigDecimal sellingPrice;

  private String status;
}
