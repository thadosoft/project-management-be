package com.example.projectmanagementbe.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(nullable = false, unique = true)
    private String title;

    @Size(max = 255)
    private String author;

    @Size(max = 100)
    private String category;

    @Size(max = 255)
    private String publisher;

    private Integer publicationYear;

    private Integer quantity_total; // tổng số bản

    private Integer quantity_available; // tổng số bản

    @Size(max = 50)
    private String location; // vị trí sách trong thư viện


}
