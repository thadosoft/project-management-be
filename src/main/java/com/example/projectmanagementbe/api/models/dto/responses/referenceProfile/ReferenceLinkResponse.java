package com.example.projectmanagementbe.api.models.dto.responses.referenceProfile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferenceLinkResponse {

  private Long id;

  private Long referenceProfileId;

  private byte[] fileData;

  private String fileName;

  private String fileType;

  private Long fileSize;

  private String fileUrl;
}
