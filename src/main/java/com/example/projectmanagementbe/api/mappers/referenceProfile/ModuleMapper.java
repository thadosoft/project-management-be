package com.example.projectmanagementbe.api.mappers.referenceProfile;

import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.ModuleRequest;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Update.UpdateReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.responses.referenceProfile.ModuleResponse;
import com.example.projectmanagementbe.api.models.referenceProfile.Modules;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceProfile;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ModuleMapper {

  Modules mapModuleRequest(ModuleRequest moduleRequest);

  ModuleResponse mapModuleResponse(Modules module);

  List<ModuleResponse> mapListModuleResponse(List<Modules> modules);

  void update(ModuleRequest dto, @MappingTarget Modules entity);

}
