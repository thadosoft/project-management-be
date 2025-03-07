package com.example.projectmanagementbe.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "material_quotation")
public class MaterialQuotation extends Auditable{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "quotation_request_id")
  private QuotationRequest quotationRequest;

  @Size(max = 255)
  @Column(name = "code")
  private String code;

  @Size(max = 255)
  @Column(name = "description")
  private String description;

  @Size(max = 255)
  @Column(name = "unit")
  private String unit;

  @Size(max = 255)
  @Column(name = "quantity")
  private String quantity;

  @Size(max = 255)
  @Column(name = "delivery_date")
  private String deliveryDate;

  @Size(max = 255)
  @Column(name = "price")
  private String price;

  @Size(max = 255)
  @Column(name = "tax")
  private String tax;

  @Size(max = 255)
  @Column(name = "price_no_tax")
  private String priceNoTax;

  @Size(max = 255)
  @Column(name = "price_tax")
  private String priceTax;

  @Size(max = 255)
  @Column(name = "total_price")
  private String totalPrice;
}