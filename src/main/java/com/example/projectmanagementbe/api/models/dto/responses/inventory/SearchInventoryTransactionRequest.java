package com.example.projectmanagementbe.api.models.dto.responses.inventory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchInventoryTransactionRequest {

  private String transactionType;

  private String processedBy;

  private String receiver;
}
