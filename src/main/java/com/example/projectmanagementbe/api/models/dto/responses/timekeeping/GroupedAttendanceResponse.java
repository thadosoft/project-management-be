package com.example.projectmanagementbe.api.models.dto.responses.timekeeping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupedAttendanceResponse {
    private String personId;
    private String personName;
    private String date;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

}

