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

    private Integer quantity_total; // tổng số bản

    private Integer quantity_available; // tổng số bản

    private String location;

}
