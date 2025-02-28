package com.example.projectmanagementbe.api.models.dto.requests.inventory.Update;

import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryTransactionRequest;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInventoryItemRequest {

  private String name;

  private String sku;

  private Long inventoryCategoryId;

  private String unit;

  private Integer quantityInStock;

  private Integer reorderLevel;

  private String location;

  private BigDecimal purchasePrice;

  private BigDecimal sellingPrice;

  private List<InventoryTransactionRequest> inventoryTransactions;
}
