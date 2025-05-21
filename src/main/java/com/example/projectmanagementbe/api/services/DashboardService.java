package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.dto.responses.dashboard.DashboardResponse;

import java.util.List;

public interface DashboardService {
    List<DashboardResponse> getProjectProgressJavaWay();
}
