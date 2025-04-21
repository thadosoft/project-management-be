package com.example.projectmanagementbe.api.services.impls.inventory;

import com.example.projectmanagementbe.api.mappers.inventory.InventoryItemMapper;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Search.SearchMaterialRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryItemResponse;
import com.example.projectmanagementbe.api.models.iventory.InventoryCategory;
import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
import com.example.projectmanagementbe.api.models.ReferenceFileV2;
import com.example.projectmanagementbe.api.repositories.inventory.InventoryCategoryRepository;
import com.example.projectmanagementbe.api.repositories.inventory.InventoryItemRepository;
import com.example.projectmanagementbe.api.repositories.ReferenceFileV2Repository;
import com.example.projectmanagementbe.api.services.inventory.IInventoryItemService;
import com.example.projectmanagementbe.exception.ApiRequestException;
import com.example.projectmanagementbe.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class InventoryItemServiceImple implements IInventoryItemService {

  private final InventoryItemRepository inventoryItemRepository;
  private final InventoryItemMapper inventoryItemMapper;
  private final InventoryCategoryRepository inventoryCategoryRepository;
  private final ReferenceFileV2Repository referenceFileV2Repository; // Add this

  @Override
  public List<InventoryItemResponse> findAll() {
    List<InventoryItem> items = inventoryItemRepository.findAll();
    items.forEach(item -> Hibernate.initialize(item.getImages()));
    return items.stream().map(inventoryItemMapper::mapInventoryItemResponse).toList();
  }

  @Override
  public Page<InventoryItemResponse> searchByParams(SearchMaterialRequest request, Pageable pageable) {
    Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));

    Page<InventoryItem> referenceProfiles = inventoryItemRepository.searchByNameAndSku(request.getName(), request.getSku(), pageable);

    List<InventoryItem> items = referenceProfiles.getContent();
    items.forEach(item -> Hibernate.initialize(item.getImages()));

    List<InventoryItemResponse> bearingResponses = items.stream()
            .map(inventoryItemMapper::mapInventoryItemResponse)
            .collect(Collectors.toList());

    return new PageImpl<>(bearingResponses, pageRequest, referenceProfiles.getTotalElements());
  }

  @Override
  public InventoryItem create(InventoryItemRequest request) {
    InventoryItem item = inventoryItemMapper.mapInventoryItem(request);
    return inventoryItemRepository.save(item);
  }

  @Override
  public void update(Long id, UpdateInventoryItemRequest request) {
    InventoryItem item = inventoryItemRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    ErrorCode.INVENTORY_ITEM_NOT_FOUND.toString()));

    InventoryCategory category = inventoryCategoryRepository.findById(request.getInventoryCategoryId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorCode.INVENTORY_CATEGORY_NOT_FOUND.getMessage()));

    item.setInventoryCategory(category);

    inventoryItemMapper.update(request, item);

    inventoryItemRepository.save(item);
  }

  @Override
  public void delete(Long id) {
    try {
      // Fetch the item with its images
      InventoryItem item = inventoryItemRepository.findById(id)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.INVENTORY_ITEM_NOT_FOUND.toString()));

      // Delete associated images
      if (item.getImages() != null && !item.getImages().isEmpty()) {
        for (ReferenceFileV2 image : item.getImages()) {
          referenceFileV2Repository.delete(image); // Delete each image
        }
        item.getImages().clear(); // Clear the relationship
        inventoryItemRepository.save(item); // Save to persist the cleared relationship
      }

      // Now delete the item
      inventoryItemRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.INVENTORY_ITEM_NOT_FOUND.toString());
    }
  }

  @Override
  public InventoryItemResponse findById(Long id) {
    InventoryItem item = inventoryItemRepository.findById(id)
            .orElseThrow(() -> new ApiRequestException(ErrorCode.INVENTORY_ITEM_NOT_FOUND));
    Hibernate.initialize(item.getImages());
    return inventoryItemMapper.mapInventoryItemResponse(item);
  }
}