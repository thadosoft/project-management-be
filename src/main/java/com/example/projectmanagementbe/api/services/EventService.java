package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.Event;
import com.example.projectmanagementbe.api.models.dto.requests.CreateEventRequest;
import com.example.projectmanagementbe.api.models.dto.requests.EventRequest;
import com.example.projectmanagementbe.api.models.dto.requests.UpdateEventRequest;
import com.example.projectmanagementbe.api.models.dto.responses.EventResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {
    Page<EventResponse> findByParams(EventRequest request, Pageable pageable);

    List<EventResponse> findAll();

    Long create(CreateEventRequest eventRequest);

    EventResponse findById(Long id);

    void update(Long id, UpdateEventRequest eventRequest);

    void delete(Long id);

}
