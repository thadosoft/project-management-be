package com.example.projectmanagementbe.api.models.dto.requests.quotation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchQuotationRequest {

  private String title;

  private String requesterName;

  private String receiverName;

  private String startDate;

  private String endDate;
}
