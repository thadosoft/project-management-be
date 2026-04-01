package com.example.projectmanagementbe.api.models.dto.responses.lunch;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class FoodResponse {

    private Long id;

    private String name;

    private BigDecimal price;

    private String category;

    private String imageUrl;

    private Boolean isAvailable;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}