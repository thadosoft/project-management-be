package com.example.projectmanagementbe.api.controllers;


import com.example.projectmanagementbe.api.models.dto.requests.CameraRequest;
import com.example.projectmanagementbe.api.models.dto.responses.CameraResponse;
import com.example.projectmanagementbe.api.services.CameraService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cameras")
@RequiredArgsConstructor
public class CameraController {
    private final CameraService cameraService;

    @PostMapping("/search")
    public Page<CameraResponse> search(@RequestBody CameraRequest request, Pageable pageable) {
        return cameraService.findByParams(request, pageable);
    }

    @GetMapping
    public ResponseEntity<List<CameraResponse>> getAll() {
        return ResponseEntity.ok(cameraService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CameraResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cameraService.findById(id));
    }
}
