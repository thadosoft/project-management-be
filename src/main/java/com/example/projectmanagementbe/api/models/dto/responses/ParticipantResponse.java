package com.example.projectmanagementbe.api.models.dto.responses;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ParticipantResponse {
    private Long eventId;
    private List<Long> participants;
}