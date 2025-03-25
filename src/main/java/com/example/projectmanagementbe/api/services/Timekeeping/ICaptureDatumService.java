package com.example.projectmanagementbe.api.services.Timekeeping;

import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.AttendanceRequest;
import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.SearchCaptureDatumRequest;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.AttendanceResponse;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CaptureDatumResponse;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICaptureDatumService {

  Page<CaptureDatumResponse> searchByParams(SearchCaptureDatumRequest request, Pageable pageable);

  BigDecimal getAttendance(AttendanceRequest request);
}