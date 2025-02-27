package com.example.projectmanagementbe.api.models.dto.responses.referenceProfile;

import com.example.projectmanagementbe.api.models.referenceProfile.Modules;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferenceProfileResponse {

  private Long id;

  private String name;

  private Modules module;

  private String description;

  private LocalDateTime createdAt;

  private List<ReferenceFileResponse> referenceFiles = new ArrayList<>();

  private List<ReferenceLinkResponse> referenceLinks = new ArrayList<>();
}
