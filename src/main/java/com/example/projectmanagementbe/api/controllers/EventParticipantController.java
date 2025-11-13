package com.example.projectmanagementbe.api.controllers;


import com.example.projectmanagementbe.api.models.EventParticipant;
import com.example.projectmanagementbe.api.models.dto.requests.ParticipantRequest;
import com.example.projectmanagementbe.api.models.dto.responses.ParticipantResponse;
import com.example.projectmanagementbe.api.services.EventParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/events")
public class EventParticipantController {

    @Autowired
    private EventParticipantService service;

    // Lấy danh sách participant
    @GetMapping("/{eventId}/participants")
    public ResponseEntity<List<Long>> getParticipants(@PathVariable Long eventId) {
        List<Long> participants = service.getParticipantsByEvent(eventId)
                .stream().map(p -> p.getId().getEmployeeId()).collect(Collectors.toList());
        return ResponseEntity.ok(participants);
    }

    // Thêm nhiều người
    @PostMapping("/{eventId}/participants")
    public ResponseEntity<ParticipantResponse> addParticipants(
            @PathVariable Long eventId,
            @RequestBody ParticipantRequest request
    ) {
        List<EventParticipant> added = service.addParticipants(eventId, request.getEmployeeIds());
        List<Long> ids = added.stream().map(p -> p.getId().getEmployeeId()).collect(Collectors.toList());
        return ResponseEntity.ok(new ParticipantResponse(eventId, ids));
    }

    // Cập nhật toàn bộ danh sách participant
    @PutMapping("/{eventId}/participants")
    public ResponseEntity<ParticipantResponse> updateParticipants(
            @PathVariable Long eventId,
            @RequestBody ParticipantRequest request
    ) {
        List<EventParticipant> updated = service.updateParticipants(eventId, request.getEmployeeIds());
        List<Long> ids = updated.stream().map(p -> p.getId().getEmployeeId()).collect(Collectors.toList());
        return ResponseEntity.ok(new ParticipantResponse(eventId, ids));
    }

    // Xóa participant
    @DeleteMapping("/{eventId}/participants/{employeeId}")
    public ResponseEntity<ParticipantResponse> removeParticipant(
            @PathVariable Long eventId,
            @PathVariable Long employeeId
    ) {
        service.removeParticipant(eventId, employeeId);
        return ResponseEntity.ok(new ParticipantResponse(eventId,
                List.of(employeeId)));
    }
}