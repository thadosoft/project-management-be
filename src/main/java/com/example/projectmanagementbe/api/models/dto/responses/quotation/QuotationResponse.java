package com.example.projectmanagementbe.api.models.dto.responses.quotation;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuotationResponse {

  private Long id;

  private String title;

  private String requesterName;

  private String requesterEmail;

  private String requesterTel;

  private String requesterAddress;

  private String requesterWebsite;

  private String receiverName;

  private String receiverEmail;

  private String receiverTel;

  private String receiverAddress;

  private String receiverWebsite;

  protected LocalDateTime createdAt;

  private List<MaterialQuotationResponse> materialQuotations;
}
