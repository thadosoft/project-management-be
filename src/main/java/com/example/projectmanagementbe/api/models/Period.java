package com.example.projectmanagementbe.api.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Date;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "period")
public class Period extends Auditable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "month")
  private Integer month;

  @Column(name = "year")
  private Integer year;

  @Column(name = "start_date", nullable = false)
  private Date startDate;

  @Column(name = "end_date", nullable = false)
  private Date endDate;

  @OneToMany(mappedBy = "periodId", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Attendance> periods;
}
