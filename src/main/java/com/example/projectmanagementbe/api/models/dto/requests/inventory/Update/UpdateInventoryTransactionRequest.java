package com.example.projectmanagementbe.api.models.dto.requests.inventory.Update;

import java.time.Instant;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInventoryTransactionRequest {

  private Long itemId;

  private String transactionType;

  private Integer quantity;

  private Date transactionDate;

  private String reason;

  private String processedBy;

  private String receiver;
}
