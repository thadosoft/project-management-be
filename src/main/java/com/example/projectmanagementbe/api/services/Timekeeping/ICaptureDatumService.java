package com.example.projectmanagementbe.api.services.Timekeeping;

import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.SearchCaptureDatumRequest;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CaptureDatumResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICaptureDatumService {
  Page<CaptureDatumResponse> searchByParams(SearchCaptureDatumRequest request, Pageable pageable);
}