package com.example.projectmanagementbe.api.models.dto.responses.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LateStaff {
    private String personId;

    private String personName;

    private String time;

    private String closeup; // column image in sqlite
}
