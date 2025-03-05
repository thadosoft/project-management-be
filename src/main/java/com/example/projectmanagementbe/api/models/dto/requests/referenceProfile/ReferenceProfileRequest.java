package com.example.projectmanagementbe.api.models.dto.requests.referenceProfile;

import com.example.projectmanagementbe.api.models.referenceProfile.Modules;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ReferenceProfileRequest {

  private String name;

  private Long module;

  private String description;

  private List<MultipartFile> referenceFiles = new ArrayList<>();

  private List<ReferenceLinkRequest> referenceLinks = new ArrayList<>();
}
