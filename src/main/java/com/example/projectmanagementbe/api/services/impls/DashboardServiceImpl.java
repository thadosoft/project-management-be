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
        return results.stream().map(row ->
                new DashboardResponse(
                        (String) row[0],
                        ((BigDecimal) row[1]).doubleValue(),
                        (String) row[2]
                )
        ).collect(Collectors.toList());
    }



}
