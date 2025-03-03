package com.example.projectmanagementbe.api.models.dto.requests.inventory.Create;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryTransactionRequest {

  private Long itemId;

  private String transactionType;

  private Integer quantity;

  private Instant transactionDate;

  private String reason;

  private String processedBy;

  private String receiver;
}
