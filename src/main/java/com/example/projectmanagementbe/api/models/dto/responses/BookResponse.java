package com.example.projectmanagementbe.api.models.dto.responses;

import com.example.projectmanagementbe.api.models.ReferenceFileV2;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

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

    private Integer quantity_total;

    private Integer quantity_available;

    private List<ReferenceFileV2> images = new ArrayList<>();
}
