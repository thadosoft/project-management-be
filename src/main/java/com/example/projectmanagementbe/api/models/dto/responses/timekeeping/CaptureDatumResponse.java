package com.example.projectmanagementbe.api.models.dto.responses.timekeeping;

import lombok.Data;

@Data
public class CaptureDatumResponse {

  private Long captureId;

  private String sequnce;

  private String deviceId;

  private String addrName;

  private String time;

  private String matchStatus;

  private String matchType;

  private String personId;

  private String personName;

  private String hatColor;

  private String wgCardId;

  private String matchFailedReson;

  private String existMask;

  private String bodyTemp;

  private String deviceSn;

  private String idcardNumber;

  private String idcardName;

  private String closeup;

  private String qRcodestatus;

  private String qRcode;

  private String tripInfor;
}
