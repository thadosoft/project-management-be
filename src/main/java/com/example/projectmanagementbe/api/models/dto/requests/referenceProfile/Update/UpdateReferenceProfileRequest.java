package com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Update;

import com.example.projectmanagementbe.api.models.referenceProfile.Modules;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateReferenceProfileRequest {

  private String name;

  private Modules module;

  private String description;

  private List<UpdateReferenceFileRequest> referenceFiles = new ArrayList<>();

  private List<UpdateReferenceLinkRequest> referenceLinks = new ArrayList<>();
}
