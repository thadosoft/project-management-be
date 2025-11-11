package com.example.projectmanagementbe.api.models;

import com.example.projectmanagementbe.api.models.project.Project;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(nullable = false, unique = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Size(max = 255)
    private String location;

    @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private EventType type;

    @NotNull(message = "Project cannot be null")
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
