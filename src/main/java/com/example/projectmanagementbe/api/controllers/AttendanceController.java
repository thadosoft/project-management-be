package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.SearchCaptureDatumRequest;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CaptureDatumResponse;
import com.example.projectmanagementbe.api.services.Timekeeping.ICaptureDatumService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attendances")
@AllArgsConstructor
public class AttendanceController {

  private final ICaptureDatumService iCaptureDatumService;

  @PostMapping("/search")
  public Page<CaptureDatumResponse> search(@RequestBody SearchCaptureDatumRequest request, Pageable pageable) {
    return iCaptureDatumService.searchByParams(request, pageable);
  }
}