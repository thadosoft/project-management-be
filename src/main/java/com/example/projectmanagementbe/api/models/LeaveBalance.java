package com.example.projectmanagementbe.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "leave_balance",
    uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "balance_year"}))
public class LeaveBalance extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "employee_id", nullable = false)
  private Long employeeId;

  @Column(name = "balance_year", nullable = false)
  private Integer year;

  /** Days granted for the year. Default 12, adjustable by an OFM. */
  @Column(name = "entitled", nullable = false)
  private Double entitled = 12.0;

  /** Days already consumed by approved requests. May push remaining negative. */
  @Column(name = "used", nullable = false)
  private Double used = 0.0;

  @Transient
  public double getRemaining() {
    double e = entitled == null ? 0.0 : entitled;
    double u = used == null ? 0.0 : used;
    return e - u;
  }
}
