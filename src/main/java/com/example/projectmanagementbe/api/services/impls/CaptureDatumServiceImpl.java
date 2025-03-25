package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.mappers.CaptureDatumMapper;
import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.AttendanceRequest;
import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.SearchCaptureDatumRequest;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.AttendanceResponse;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CaptureDatumResponse;
import com.example.projectmanagementbe.api.repositories.CaptureDatumRepository;
import com.example.projectmanagementbe.api.services.Timekeeping.ICaptureDatumService;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Override
  @Transactional
  public BigDecimal getAttendance(AttendanceRequest request) {

    Object[] results = captureDatumRepository.getAttendanceData(request.getDate(), request.getEmpCode());

    return (BigDecimal) results[0];
  }
}
