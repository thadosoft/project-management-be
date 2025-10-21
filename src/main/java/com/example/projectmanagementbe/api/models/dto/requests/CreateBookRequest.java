package com.example.projectmanagementbe.api.models.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequest {
    private String title;
    private String author;
    private String category;
    private String publisher;
    private Integer publicationYear;
    private Integer quantity_total;
    private Integer quantity_available;
}
