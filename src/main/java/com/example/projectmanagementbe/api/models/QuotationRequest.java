package com.example.projectmanagementbe.api.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "quotation_request")
public class QuotationRequest extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 255)
  @NotNull
  @Column(name = "requester_name", nullable = false)
  private String requesterName;

  @Size(max = 255)
  @NotNull
  @Column(name = "requester_email", nullable = false)
  private String requesterEmail;

  @Size(max = 50)
  @NotNull
  @Column(name = "requester_tel", nullable = false, length = 50)
  private String requesterTel;

  @Column(name = "requester_address")
  private String requesterAddress;

  @Size(max = 255)
  @Column(name = "requester_website")
  private String requesterWebsite;

  @Size(max = 255)
  @NotNull
  @Column(name = "receiver_name", nullable = false)
  private String receiverName;

  @Size(max = 255)
  @NotNull
  @Column(name = "receiver_email", nullable = false)
  private String receiverEmail;

  @Size(max = 50)
  @NotNull
  @Column(name = "receiver_tel", nullable = false, length = 50)
  private String receiverTel;

  @Column(name = "receiver_address")
  private String receiverAddress;

  @Size(max = 255)
  @Column(name = "receiver_website")
  private String receiverWebsite;

  @OneToMany(mappedBy = "quotationRequest", cascade = CascadeType.ALL)
  private List<MaterialQuotation> materialQuotations;
}