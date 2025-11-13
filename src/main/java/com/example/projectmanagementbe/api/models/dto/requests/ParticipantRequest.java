package com.example.projectmanagementbe.api.models.dto.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParticipantRequest {
    private List<Long> employeeIds;
}