package com.example.projectmanagementbe.api.services.referenceProfile;

import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.ModuleRequest;
import com.example.projectmanagementbe.api.models.dto.responses.referenceProfile.ModuleResponse;
import java.util.List;

public interface IModuleService {

  List<ModuleResponse> findAll();

  void create(ModuleRequest moduleRequest);

  void update(Long id, ModuleRequest moduleRequest);

  void delete(Long id);

  ModuleResponse findById(Long id);
}
