package com.example.projectmanagementbe.api.models.dto.responses.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DashboardResponse {

    private String projectName;

    private double progressPercentage;

    private String status;
}
