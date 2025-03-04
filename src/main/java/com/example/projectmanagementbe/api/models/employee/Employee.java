package com.example.projectmanagementbe.api.models.employee;

import com.example.projectmanagementbe.api.models.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 50)
  @Column(name = "username", length = 50)
  private String username;

  @Column(name = "avatar", length = 255)
  private String avatar;

  @Size(max = 255)
  @Column(name = "full_name")
  private String fullName;

  @Size(max = 50)
  @Column(name = "career", length = 50)
  private String career;

  @Size(max = 50)
  @Column(name = "place_of_birth", length = 50)
  private String placeOfBirth;

  @Size(max = 50)
  @Column(name = "nation", length = 50)
  private String nation;

  @Size(max = 50)
  @Column(name = "gender", length = 50)
  private String gender;

  @Size(max = 50)
  @Column(name = "tax", length = 50)
  private String tax;

  @Size(max = 50)
  @Column(name = "email", length = 50)
  private String email;

  @Size(max = 50)
  @Column(name = "phone", length = 50)
  private String phone;

  @Size(max = 50)
  @Column(name = "company_email", length = 50)
  private String companyEmail;

  @Size(max = 50)
  @Column(name = "emergency_contact", length = 50)
  private String emergencyContact;

  @Size(max = 255)
  @Column(name = "house_hold_address")
  private String houseHoldAddress;

  @Size(max = 255)
  @Column(name = "current_address")
  private String currentAddress;

  @Size(max = 255)
  @Column(name = "description")
  private String description;
}