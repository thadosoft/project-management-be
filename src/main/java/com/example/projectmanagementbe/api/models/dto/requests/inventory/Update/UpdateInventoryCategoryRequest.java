package com.example.projectmanagementbe.api.models.dto.requests.inventory.Update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInventoryCategoryRequest {

  private String name;

  private String code;

  private String description;
}
