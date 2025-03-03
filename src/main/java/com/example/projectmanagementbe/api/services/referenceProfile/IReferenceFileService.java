package com.example.projectmanagementbe.api.services.referenceProfile;

import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.ModuleRequest;
import com.example.projectmanagementbe.api.models.dto.responses.referenceProfile.ModuleResponse;
import com.example.projectmanagementbe.api.models.dto.responses.referenceProfile.ReferenceProfileResponse;
import java.util.List;

public interface IReferenceFileService {

  List<ReferenceProfileResponse> findAll();

  void create(ModuleRequest moduleRequest);

  void update(String id, ModuleRequest moduleRequest);

  void delete(String id);

  ModuleResponse findById(String id);
}
