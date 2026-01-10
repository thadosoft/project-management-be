package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.Camera;
import com.example.projectmanagementbe.api.models.dto.responses.CameraResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CameraMapper {

    // Map Camera entity -> CameraResponse DTO
    CameraResponse mapCameraResponse(Camera camera);
}
