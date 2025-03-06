package com.example.projectmanagementbe.api.mappers.referenceProfile;

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
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReferenceProfileMapper {

  ReferenceProfileResponse mapReferenceProfileResponse(ReferenceProfile referenceProfile);

  ReferenceProfile mapReferenceProfile(ReferenceProfileRequest referenceProfileRequest);

  ReferenceProfile mapReferenceProfile(Long id);

  Modules map(Long id);

  void updateReferenceProfile(UpdateReferenceProfileRequest dto, @MappingTarget ReferenceProfile entity);

  List<ReferenceFile> mapReferenceFiles(List<UpdateReferenceFileRequest> files);

  List<ReferenceLink> mapReferenceLinks(List<UpdateReferenceLinkRequest> links);
}
