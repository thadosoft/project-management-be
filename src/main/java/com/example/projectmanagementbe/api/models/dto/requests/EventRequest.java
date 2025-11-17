package com.example.projectmanagementbe.api.models.dto.requests;

import com.example.projectmanagementbe.api.models.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventRequest {

    private String title;

    private String startDate;

    private String endDate;

    private String type;

    private Integer month;   // dùng khi lọc theo tháng

    private Integer quarter;    // quý (nếu lọc theo quý)

    private Integer year;

    private Long participantIds;
}
