package com.example.projectmanagementbe.api.models.dto.requests.inventory.Create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryCategoryRequest {

  private String name;

  private String code;

  private String description;
}
