package com.example.projectmanagementbe.api.models.dto.requests.referenceProfile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferenceLinkRequest {

  private Long referenceProfile;

  private String link;

  private String description;
}
