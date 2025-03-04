package com.example.projectmanagementbe.api.services.referenceProfile;

import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.ReferenceLinkRequest;

public interface IProfileReferenceLink {

  void createReferenceLink(ReferenceLinkRequest request);
}
