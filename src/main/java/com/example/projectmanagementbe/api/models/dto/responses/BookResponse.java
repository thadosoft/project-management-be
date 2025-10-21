package com.example.projectmanagementbe.api.models.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private String category;
    private String publisher;
    private Integer publicationYear;
    private Integer quantity;
    private Boolean available;
}
