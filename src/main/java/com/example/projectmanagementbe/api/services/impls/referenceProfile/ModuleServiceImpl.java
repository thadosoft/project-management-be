package com.example.projectmanagementbe.api.services.impls.referenceProfile;

import com.example.projectmanagementbe.api.mappers.referenceProfile.ModuleMapper;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.ModuleRequest;
import com.example.projectmanagementbe.api.models.dto.responses.referenceProfile.ModuleResponse;
import com.example.projectmanagementbe.api.models.referenceProfile.Modules;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceFile;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceLink;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceProfile;
import com.example.projectmanagementbe.api.repositories.referenceProfile.ModuleRepository;
import com.example.projectmanagementbe.api.services.referenceProfile.IModuleService;
import com.example.projectmanagementbe.exception.ApiRequestException;
import com.example.projectmanagementbe.exception.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements IModuleService {

  private final ModuleRepository moduleRepository;
  private final ModuleMapper moduleMapper;

  @Override
  public List<ModuleResponse> findAll() {
    return moduleMapper.mapListModuleResponse(moduleRepository.findAll());
  }

  @Override
  public void create(ModuleRequest moduleRequest) {
    Modules module = moduleMapper.mapModuleRequest(moduleRequest);
    moduleRepository.save(module);
  }

  @Override
  @Transactional
  public void update(Long id, ModuleRequest moduleRequest) {
    Modules module = moduleRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.REFERENCE_PROFILE_NOT_FOUND.toString()));

    moduleMapper.update(moduleRequest, module);

    moduleRepository.save(module);
  }

  @Override
  public void delete(Long id) {
    try {
      moduleRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.MODULE_NOT_FOUND.toString());
    }
  }

  @Override
  public ModuleResponse findById(Long id) {
    return moduleMapper.mapModuleResponse(moduleRepository.findById(id).orElseThrow(() -> new ApiRequestException(ErrorCode.MODULE_NOT_FOUND)));
  }
}
