package com.example.projectmanagementbe.api.models.dto.requests.inventory.Search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchMaterialRequest {

  private String name;

  private String sku;
}
