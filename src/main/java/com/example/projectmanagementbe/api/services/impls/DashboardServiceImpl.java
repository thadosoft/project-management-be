package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.models.dto.responses.dashboard.DashboardResponse;
import com.example.projectmanagementbe.api.repositories.project.ProjectRepository;
import com.example.projectmanagementbe.api.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final ProjectRepository projectRepository;

    @Override
    public List<DashboardResponse> getProjectProgressJavaWay() {
        List<Object[]> results = projectRepository.fetchProjectProgress();
        return results.stream()
                .map(row -> {
                    String projectName = row[0] != null ? row[0].toString() : null;
                    Double progress = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
                    String status = row[2] != null ? row[2].toString() : null;
                    return new DashboardResponse(projectName, progress, status);
                })
                .collect(Collectors.toList());
    }

}
