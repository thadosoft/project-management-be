package com.example.projectmanagementbe.api.services.impls.attandance;

import com.example.projectmanagementbe.api.mappers.CaptureDatumMapper;
import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.AttendanceRequest;
import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.SearchCaptureDatumRequest;
import com.example.projectmanagementbe.api.models.dto.responses.dashboard.LateStaff;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CaptureDatumResponse;
import com.example.projectmanagementbe.api.models.employee.CaptureDatum;
import com.example.projectmanagementbe.api.repositories.attandance.CaptureDatumRepository;
import com.example.projectmanagementbe.api.services.attandance.ICaptureDatumService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    public List<LateStaff> getLatest6CaptureData() {
        List<CaptureDatum> captureDatumList = captureDatumRepository.findTop4ByOrderByCreatedAtDesc();
        return mapper.map(captureDatumList);
    }

}
