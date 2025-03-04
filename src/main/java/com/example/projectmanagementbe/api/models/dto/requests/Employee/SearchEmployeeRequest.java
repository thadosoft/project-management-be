package com.example.projectmanagementbe.api.models.dto.requests.Employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchEmployeeRequest {

  private String fullName;

  private String career;
}
