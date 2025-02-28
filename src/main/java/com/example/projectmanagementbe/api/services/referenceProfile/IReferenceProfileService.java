package com.example.projectmanagementbe.api.services.referenceProfile;

import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.ReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Search.SearchReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Update.UpdateReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.responses.referenceProfile.ReferenceProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReferenceProfileService {

  Page<ReferenceProfileResponse> findAll(Pageable pageable);

  Page<ReferenceProfileResponse> searchByParams(SearchReferenceProfileRequest searchReferenceProfileRequest, Pageable pageable);

  void create(ReferenceProfileRequest referenceProfileRequest);

  void update(Long id, UpdateReferenceProfileRequest ReferenceProfileRequest);

  void delete(Long id);

  ReferenceProfileResponse findById(Long id);
}
