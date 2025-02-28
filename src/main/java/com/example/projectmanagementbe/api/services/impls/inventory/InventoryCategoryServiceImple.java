package com.example.projectmanagementbe.api.services.impls.inventory;

import com.example.projectmanagementbe.api.mappers.inventory.InventoryCategoryMapper;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryCategoryRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryCategoryRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryCategoryResponse;
import com.example.projectmanagementbe.api.models.iventory.InventoryCategory;
import com.example.projectmanagementbe.api.repositories.inventory.InventoryCategoryRepository;
import com.example.projectmanagementbe.api.services.inventory.IInventoryCategoryService;
import com.example.projectmanagementbe.exception.ApiRequestException;
import com.example.projectmanagementbe.exception.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class InventoryCategoryServiceImple implements IInventoryCategoryService {

  private final InventoryCategoryRepository inventoryCategoryRepository;
  private final InventoryCategoryMapper categoryMapper;

  @Override
  public List<InventoryCategoryResponse> findAll() {
    return inventoryCategoryRepository.findAll().stream().map(categoryMapper::mapInventoryCategoryResponse).toList();
  }

  @Override
  public void create(InventoryCategoryRequest inventoryCategoryRequest) {
    inventoryCategoryRepository.save(categoryMapper.mapInventoryCategory(inventoryCategoryRequest));
  }

  @Override
  @Transactional
  public void update(Long id, UpdateInventoryCategoryRequest inventoryCategoryRequest) {
    InventoryCategory category = inventoryCategoryRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            ErrorCode.INVENTORY_CATEGORY_NOT_FOUND.toString()));

    categoryMapper.update(inventoryCategoryRequest, category);
  }

  @Override
  public void delete(Long id) {
    try {
      inventoryCategoryRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.INVENTORY_CATEGORY_NOT_FOUND.toString());
    }
  }

  @Override
  public InventoryCategoryResponse findById(Long id) {
    return categoryMapper.mapInventoryCategoryResponse(inventoryCategoryRepository.findById(id).orElseThrow(() -> new ApiRequestException(ErrorCode.INVENTORY_CATEGORY_NOT_FOUND)));
  }
}
