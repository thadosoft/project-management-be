package com.example.projectmanagementbe.api.models.dto.responses.referenceProfile;

import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceProfile;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleResponse {

  public Long id;

  private String title;

  private String description;

  private List<ReferenceProfileResponse> referenceProfiles;
}
