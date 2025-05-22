package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.responses.dashboard.DashboardResponse;
import com.example.projectmanagementbe.api.services.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@AllArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<List<DashboardResponse>> dashboardInfo() {
        return ResponseEntity.ok(dashboardService.getProjectProgressJavaWay());
    }
}