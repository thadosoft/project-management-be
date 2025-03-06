package com.example.projectmanagementbe.api.mappers.referenceProfile;

import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.ReferenceLinkRequest;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.ReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Update.UpdateReferenceFileRequest;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Update.UpdateReferenceLinkRequest;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Update.UpdateReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.responses.referenceProfile.ReferenceProfileResponse;
import com.example.projectmanagementbe.api.models.referenceProfile.Modules;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceFile;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceLink;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceProfile;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReferenceLinkMapper {
  ReferenceProfile map(Long value);
  ReferenceLink mapReferenceLink(ReferenceLinkRequest request);
}
