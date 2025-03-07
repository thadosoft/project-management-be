package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.mappers.CaptureDatumMapper;
import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.SearchCaptureDatumRequest;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CaptureDatumResponse;
import com.example.projectmanagementbe.api.repositories.CaptureDatumRepository;
import com.example.projectmanagementbe.api.services.Timekeeping.ICaptureDatumService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    LocalDateTime startDate = parseDateToLocalDateTime(request.getStartDate(), false); // false nghĩa là không cần end-of-day
    LocalDateTime endDate = parseDateToLocalDateTime(request.getEndDate(), true);
    return captureDatumRepository.findByParams(request.getPersonName(), startDate, endDate, pageable).map(mapper::map);
  }

  private LocalDateTime parseDateToLocalDateTime(String dateStr, boolean isEndDate) {
    if (dateStr == null || dateStr.isEmpty()) {
      return null;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(dateStr, formatter);

    if (isEndDate) {
      return localDate.atTime(23, 59, 59);
    } else {
      return localDate.atStartOfDay();
    }
  }
}
