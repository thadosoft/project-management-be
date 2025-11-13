package com.example.projectmanagementbe.api.models;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventParticipant {

    @EmbeddedId
    private EventParticipantKey id;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();

    // Constructor tiện lợi
    public EventParticipant(Long eventId, Long employeeId) {
        this.id = new EventParticipantKey(eventId, employeeId);
    }

    // Static inner class làm composite key
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter      // Bắt buộc để getEmployeeId() và getEventId() hoạt động
    @Setter
    @EqualsAndHashCode
    public static class EventParticipantKey implements Serializable {
        private Long eventId;
        private Long employeeId;
    }
}
