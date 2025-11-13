package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.EventParticipant;

import java.util.List;

public interface EventParticipantService {
    List<EventParticipant> getParticipantsByEvent(Long eventId);
    List<EventParticipant> addParticipants(Long eventId, List<Long> employeeIds);
    List<EventParticipant> updateParticipants(Long eventId, List<Long> employeeIds);
    void removeParticipant(Long eventId, Long employeeId);
}