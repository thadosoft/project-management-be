package com.example.projectmanagementbe.api.models.dto.requests.referenceProfile;

import com.example.projectmanagementbe.api.models.referenceProfile.Modules;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferenceProfileRequest {

  private String name;

  private Modules module;

  private String description;

  private List<ReferenceFileRequest> referenceFiles = new ArrayList<>();

  private List<ReferenceLinkRequest> referenceLinks = new ArrayList<>();
}
