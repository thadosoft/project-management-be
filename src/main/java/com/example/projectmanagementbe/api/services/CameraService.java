package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.dto.requests.CameraRequest;
import com.example.projectmanagementbe.api.models.dto.responses.CameraResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CameraService {

    List<CameraResponse> findAll();

    CameraResponse findById(Long id);

    Page<CameraResponse> findByParams(CameraRequest request, Pageable pageable);

}
