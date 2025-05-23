package com.example.projectmanagementbe.api.models;

import com.example.projectmanagementbe.api.models.employee.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "employee_of_month")
public class EmployeeOfMonth extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "month_year")
    private LocalDate monthYear;

    @Column(name = "reason")
    private String reason;

    @Column(name = "month")
    private Integer month;

    @Column(name = "year")
    private Integer year;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "award_date")
    private LocalDateTime awardDate;

}