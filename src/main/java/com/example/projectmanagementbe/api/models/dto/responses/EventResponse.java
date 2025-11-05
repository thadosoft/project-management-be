package com.example.projectmanagementbe.api.models.dto.responses;


import com.example.projectmanagementbe.api.models.EventType;
import com.example.projectmanagementbe.api.models.dto.responses.project.ProjectResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventResponse {
    private Long id;

    private String title;

    private String description;

    private String location;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private EventType type;

    private ProjectResponse project;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long durationDays;
}
