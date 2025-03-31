package com.example.projectmanagementbe.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "attendance")
public class Attendance extends Auditable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "employee_code", nullable = false)
  private String employeeCode;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(name = "work_date", nullable = false)
  private LocalDate workDate;

  @Column(name = "shift_name")
  private String shiftName;

  @Column(name = "total_shifts")
  private BigDecimal totalShifts;

  @Column(name = "morning_checkin")
  private String morningCheckin;

  @Column(name = "afternoon_checkout")
  private String afternoonCheckout;

  @Column(name = "other_checkins")
  private String otherCheckins;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "period_id")
  private Period periodId;
}
