package com.example.projectmanagementbe.api.models.dto.responses.inventory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryCategoryResponse {

  private Long id;

  private String name;

  private String code;

  private String description;
}
