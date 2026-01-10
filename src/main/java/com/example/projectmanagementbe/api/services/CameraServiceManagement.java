package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.mappers.CameraMapper;
import com.example.projectmanagementbe.api.models.Camera;
import com.example.projectmanagementbe.api.models.dto.requests.CameraRequest;
import com.example.projectmanagementbe.api.models.dto.responses.CameraResponse;
import com.example.projectmanagementbe.api.repositories.CameraRepository;
import com.example.projectmanagementbe.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;


import java.util.List;

@Service
@RequiredArgsConstructor
public class CameraServiceManagement implements CameraService {

    private final CameraRepository cameraRepository;
    private final CameraMapper cameraMapper;

    @Override
    public List<CameraResponse> findAll() {
        List<Camera> cameras = cameraRepository.findAll();
        return cameras.stream()
                .map(cameraMapper::mapCameraResponse)
                .toList();
    }

    @Override
    public CameraResponse findById(Long id) {
        return cameraRepository.findById(id)
                .map(cameraMapper::mapCameraResponse)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                ErrorCode.CAMERA_NOT_FOUND.getMessage()
                        )
                );
    }

    @Override
    public Page<CameraResponse> findByParams(CameraRequest request, Pageable pageable) {

        String modelName = request.getModelName();
        String type = request.getType();

        // Nếu không có filter nào -> lấy tất cả
        boolean noFilter =
                (modelName == null || modelName.isBlank())
                        && (type == null || type.isBlank());

        Page<Camera> result;

        if (noFilter) {
            result = cameraRepository.findAll(pageable);
        } else {
            result = cameraRepository.findByParams(modelName, type, pageable);
        }

        return result.map(cameraMapper::mapCameraResponse);
    }

}
