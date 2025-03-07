package com.example.projectmanagementbe.api.models.dto.requests.quotation;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateQuotationRequest {

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

  private List<MaterialQuotationRequest> materialQuotations;
}
