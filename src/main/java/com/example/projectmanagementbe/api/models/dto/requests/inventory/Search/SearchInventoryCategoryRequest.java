package com.example.projectmanagementbe.api.models.dto.requests.inventory.Search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchInventoryCategoryRequest {

  private String name;

  private String code;

  private String description;
}
