package com.example.projectmanagementbe.api.models.employee;

import com.example.projectmanagementbe.api.models.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "capture_data")
public class CaptureDatum extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 255)
  @Column(name = "capture_id")
  private Long captureId;

  @Size(max = 255)
  @Column(name = "sequnce")
  private String sequnce;

  @Size(max = 255)
  @Column(name = "device_id")
  private String deviceId;

  @Size(max = 255)
  @Column(name = "addr_name")
  private String addrName;

  @Size(max = 255)
  @Column(name = "time")
  private String time;

  @Size(max = 255)
  @Column(name = "match_status")
  private String matchStatus;

  @Size(max = 255)
  @Column(name = "match_type")
  private String matchType;

  @Size(max = 255)
  @Column(name = "person_id")
  private String personId;

  @Size(max = 255)
  @Column(name = "person_name")
  private String personName;

  @Size(max = 255)
  @Column(name = "hatColor")
  private String hatColor;

  @Size(max = 255)
  @Column(name = "wg_card_id")
  private String wgCardId;

  @Size(max = 255)
  @Column(name = "match_failed_reson")
  private String matchFailedReson;

  @Size(max = 255)
  @Column(name = "exist_mask")
  private String existMask;

  @Size(max = 255)
  @Column(name = "body_temp")
  private String bodyTemp;

  @Size(max = 255)
  @Column(name = "device_sn")
  private String deviceSn;

  @Size(max = 255)
  @Column(name = "idcard_number")
  private String idcardNumber;

  @Size(max = 255)
  @Column(name = "idcard_name")
  private String idcardName;

  @Size(max = 255)
  @Column(name = "closeup")
  private String closeup;

  @Size(max = 255)
  @Column(name = "QRcodestatus")
  private String qRcodestatus;

  @Size(max = 255)
  @Column(name = "QRcode")
  private String qRcode;

  @Size(max = 255)
  @Column(name = "trip_infor")
  private String tripInfor;

}