package com.example.projectmanagementbe.api.models.dto.responses.inventory;

import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryTransactionResponse {

  private Long id;

  private Long itemId;

  private String transactionType;

  private Integer quantity;

  private Instant transactionDate;

  private String reason;

  private String processedBy;

  private String receiver;
}
