package com.example.projectmanagementbe.api.controllers;


import com.example.projectmanagementbe.api.models.Event;
import com.example.projectmanagementbe.api.models.dto.requests.*;
import com.example.projectmanagementbe.api.models.dto.responses.BookLoanResponse;
import com.example.projectmanagementbe.api.models.dto.responses.EventResponse;
import com.example.projectmanagementbe.api.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/search")
    public Page<EventResponse> search(@RequestBody EventRequest request, Pageable pageable) {
        return eventService.findByParams(request, pageable);
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAll() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CreateEventRequest request) {
        Long eventId = eventService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateEventRequest request) {
        eventService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
