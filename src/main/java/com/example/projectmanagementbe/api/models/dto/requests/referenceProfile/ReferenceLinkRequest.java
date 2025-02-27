package com.example.projectmanagementbe.api.models.dto.requests.referenceProfile;

import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceProfile;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferenceLinkRequest {

  private Long referenceProfileId;

  private byte[] fileData;

  private String fileName;

  private String fileType;

  private Long fileSize;

  private String fileUrl;
}
