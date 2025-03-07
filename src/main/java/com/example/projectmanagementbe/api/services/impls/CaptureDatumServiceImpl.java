package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.mappers.CaptureDatumMapper;
import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.SearchCaptureDatumRequest;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CaptureDatumResponse;
import com.example.projectmanagementbe.api.repositories.CaptureDatumRepository;
import com.example.projectmanagementbe.api.services.Timekeeping.ICaptureDatumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaptureDatumServiceImpl implements ICaptureDatumService {

  private final CaptureDatumRepository captureDatumRepository;
  private final CaptureDatumMapper mapper;

  @Override
  public Page<CaptureDatumResponse> searchByParams(SearchCaptureDatumRequest request, Pageable pageable) {
    System.out.println("Cjhau vuod dao");
    return captureDatumRepository.findByParams(request.getPersonName(), request.getStartDate(), request.getEndDate(), pageable).map(mapper::map);
  }
}
