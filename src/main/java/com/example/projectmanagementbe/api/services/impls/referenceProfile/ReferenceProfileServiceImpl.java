package com.example.projectmanagementbe.api.services.impls.referenceProfile;

import com.example.projectmanagementbe.api.mappers.referenceProfile.ReferenceProfileMapper;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.ReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Search.SearchReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Update.UpdateReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.responses.referenceProfile.ReferenceProfileResponse;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceFile;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceLink;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceProfile;
import com.example.projectmanagementbe.api.repositories.referenceProfile.ReferenceProfileRepository;
import com.example.projectmanagementbe.api.services.referenceProfile.IReferenceProfileService;
import com.example.projectmanagementbe.exception.ApiRequestException;
import com.example.projectmanagementbe.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReferenceProfileServiceImpl implements IReferenceProfileService {

  private final ReferenceProfileRepository referenceProfileRepository;
  private final ReferenceProfileMapper referenceProfileMapper;

  @Override
  public Page<ReferenceProfileResponse> findAll(Pageable pageable) {
    Pageable request = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));

    Page<ReferenceProfile> referenceProfiles = referenceProfileRepository.findAll(request);

    List<ReferenceProfileResponse> bearingResponses = referenceProfiles.getContent().stream().map(referenceProfileMapper::mapReferenceProfileResponse).collect(Collectors.toList());

    return new PageImpl<>(bearingResponses, request, referenceProfiles.getTotalElements());
  }

  @Override
  public Page<ReferenceProfileResponse> searchByParams(SearchReferenceProfileRequest searchReferenceProfileRequest, Pageable pageable) {
    return referenceProfileRepository
        .searchByNameAndDate(searchReferenceProfileRequest.getName(), searchReferenceProfileRequest.getStartDate(), searchReferenceProfileRequest.getEndDate(), pageable).map(referenceProfileMapper::mapReferenceProfileResponse);
  }

  @Override
  public void create(ReferenceProfileRequest referenceProfileRequest) {
    ReferenceProfile referenceProfile = referenceProfileMapper.mapReferenceProfile(referenceProfileRequest);

    referenceProfile.getReferenceFiles().forEach(file -> file.setReferenceProfile(referenceProfile));
    referenceProfile.getReferenceLinks().forEach(link -> link.setReferenceProfile(referenceProfile));

    referenceProfileRepository.save(referenceProfile);
  }

  @Override
  public void update(Long id, UpdateReferenceProfileRequest referenceProfileRequest) {
    ReferenceProfile referenceProfile = referenceProfileRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.REFERENCE_PROFILE_NOT_FOUND.toString()));

    referenceProfileMapper.updateReferenceProfile(referenceProfileRequest, referenceProfile);

    for (ReferenceFile file : referenceProfile.getReferenceFiles()) {
      file.setReferenceProfile(referenceProfile);
    }

    for (ReferenceLink link : referenceProfile.getReferenceLinks()) {
      link.setReferenceProfile(referenceProfile);
    }

    referenceProfileRepository.save(referenceProfile);
  }


  @Override
  public void delete(Long id) {
    if (referenceProfileRepository.existsById(id)) {
      referenceProfileRepository.deleteById(id);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.REFERENCE_PROFILE_NOT_FOUND.toString());
    }
  }

  @Override
  public ReferenceProfileResponse findById(Long id) {
    return referenceProfileMapper.mapReferenceProfileResponse(referenceProfileRepository.findById(id).orElseThrow(() -> new ApiRequestException(ErrorCode.REFERENCE_PROFILE_NOT_FOUND)));
  }
}
