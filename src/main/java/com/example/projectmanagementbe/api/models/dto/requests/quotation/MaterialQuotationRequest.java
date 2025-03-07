package com.example.projectmanagementbe.api.models.dto.requests.quotation;

import com.example.projectmanagementbe.api.models.QuotationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialQuotationRequest {

  private QuotationRequest quotationRequest;

  private String code;

  private String description;

  private String unit;

  private String quantity;

  private String deliveryDate;

  private String price;

  private String tax;

  private String priceNoTax;

  private String priceTax;

  private String totalPrice;
}
