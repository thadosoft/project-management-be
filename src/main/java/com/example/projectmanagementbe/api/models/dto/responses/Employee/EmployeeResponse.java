package com.example.projectmanagementbe.api.models.dto.responses.Employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse {

  private Long id;

  private String username;

  private String avatar;

  private String fullName;

  private String career;

  private String placeOfBirth;

  private String nation;

  private String gender;

  private String tax;

  private String email;

  private String phone;

  private String companyEmail;

  private String emergencyContact;

  private String houseHoldAddress;

  private String currentAddress;

  private String description;
}
