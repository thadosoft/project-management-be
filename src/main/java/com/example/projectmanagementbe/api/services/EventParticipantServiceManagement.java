package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.EventParticipant;
import com.example.projectmanagementbe.api.repositories.EventParticipantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventParticipantServiceManagement implements EventParticipantService {

    @Autowired
    private EventParticipantRepository repository;

    @Override
    public List<EventParticipant> getParticipantsByEvent(Long eventId) {
        return repository.findAllByIdEventId(eventId);
    }

    @Override
    @Transactional
    public List<EventParticipant> addParticipants(Long eventId, List<Long> employeeIds) {
        List<EventParticipant> participants = employeeIds.stream()
                .map(empId -> new EventParticipant(eventId, empId))
                .collect(Collectors.toList());
        return repository.saveAll(participants);
    }

    @Override
    @Transactional
    public List<EventParticipant> updateParticipants(Long eventId, List<Long> employeeIds) {
        // xóa tất cả participant cũ
        repository.deleteAllByIdEventId(eventId);

        // thêm mới
        return addParticipants(eventId, employeeIds);
    }

    @Override
    public void removeParticipant(Long eventId, Long employeeId) {
        repository.deleteById(new EventParticipant.EventParticipantKey(eventId, employeeId));
    }
}