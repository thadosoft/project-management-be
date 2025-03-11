package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.mappers.CaptureDatumMapper;
import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.SearchCaptureDatumRequest;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CaptureDatumResponse;
import com.example.projectmanagementbe.api.repositories.CaptureDatumRepository;
import com.example.projectmanagementbe.api.services.Timekeeping.ICaptureDatumService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static com.example.projectmanagementbe.auth.utils.StringToLocalDateTime.parseDateToLocalDateTime;
import static com.example.projectmanagementbe.auth.utils.StringToLocalDateTime.parseDateToLocalDateTime2;

@Service
@RequiredArgsConstructor
public class CaptureDatumServiceImpl implements ICaptureDatumService {

  private final CaptureDatumRepository captureDatumRepository;
  private final CaptureDatumMapper mapper;

  @Override
  public Page<CaptureDatumResponse> searchByParams(SearchCaptureDatumRequest request, Pageable pageable) {
    LocalDateTime startDate = parseDateToLocalDateTime2(request.getStartDate(), false);
    LocalDateTime endDate = parseDateToLocalDateTime2(request.getEndDate(), true);
    return captureDatumRepository.findByParams(request.getPersonName(), startDate, endDate, pageable).map(mapper::map);
  }
}
