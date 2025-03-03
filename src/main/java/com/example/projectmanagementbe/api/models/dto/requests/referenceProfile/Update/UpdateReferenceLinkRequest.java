package com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateReferenceLinkRequest {

  private Long id;

  private Long referenceProfileId;

  private byte[] fileData;

  private String fileName;

  private String fileType;

  private Long fileSize;

  private String fileUrl;
}
