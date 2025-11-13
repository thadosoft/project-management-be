package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.api.models.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, EventParticipant.EventParticipantKey> {
    List<EventParticipant> findAllByIdEventId(Long eventId);
    void deleteAllByIdEventId(Long eventId);
}