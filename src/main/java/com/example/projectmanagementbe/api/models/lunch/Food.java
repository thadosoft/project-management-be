package com.example.projectmanagementbe.api.models.lunch;

import com.example.projectmanagementbe.api.models.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "menu_items")
public class Food extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String category;

    @Size(max = 500)
    @Column(length = 500)
    private String imageUrl;

    @Column(nullable = false)
    private Boolean isAvailable = true;
}