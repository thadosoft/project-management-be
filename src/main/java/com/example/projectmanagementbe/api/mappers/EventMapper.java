package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.Event;
import com.example.projectmanagementbe.api.models.dto.requests.CreateEventRequest;
import com.example.projectmanagementbe.api.models.dto.requests.EventRequest;
import com.example.projectmanagementbe.api.models.dto.requests.UpdateEventRequest;
import com.example.projectmanagementbe.api.models.dto.responses.EventResponse;
import com.example.projectmanagementbe.api.models.project.Project;
import org.mapstruct.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface EventMapper {

    // Map Event entity -> EventResponse DTO
    EventResponse mapEventResponse(Event event);

    // Map EventRequest -> Event entity
    @Mapping(target = "project", expression = "java(mapProjectFromId(request.getProjectId()))")
    @Mapping(target = "startDate", expression = "java(request.getStartDate() != null ? request.getStartDate() : LocalDateTime.now())")
    @AfterMapping
    default void calculateDuration(@MappingTarget EventResponse response, Event entity) {
        if (entity.getStartDate() != null && entity.getEndDate() != null) {
            response.setDurationDays(
                    Duration.between(entity.getStartDate(), entity.getEndDate()).toDays()
            );
        }
    }

    Event mapCreate(CreateEventRequest request);

    void update(UpdateEventRequest dto, @MappingTarget Event entity);

    // helper method để map projectId -> Project entity (chỉ cần id)
    default Project mapProjectFromId(String id) {
        if (id == null) return null;
        Project project = new Project();
        project.setId(id);
        return project;
    }
}
