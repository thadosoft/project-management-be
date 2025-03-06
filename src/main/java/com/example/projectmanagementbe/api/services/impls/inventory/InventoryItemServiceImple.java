package com.example.projectmanagementbe.api.services.impls.inventory;

import com.example.projectmanagementbe.api.mappers.inventory.InventoryItemMapper;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Search.SearchMaterialRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryItemRequest;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Search.SearchReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryItemResponse;
import com.example.projectmanagementbe.api.models.dto.responses.referenceProfile.ReferenceProfileResponse;
import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceProfile;
import com.example.projectmanagementbe.api.repositories.inventory.InventoryItemRepository;
import com.example.projectmanagementbe.api.services.inventory.IInventoryItemService;
import com.example.projectmanagementbe.exception.ApiRequestException;
import com.example.projectmanagementbe.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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

  @Override
  public List<InventoryItemResponse> findAll() {
    return inventoryItemRepository.findAll().stream().map(inventoryItemMapper::mapInventoryItemResponse).toList();
  }

  @Override
  public Page<InventoryItemResponse> searchByParams(SearchMaterialRequest request, Pageable pageable) {
    Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));

    Page<InventoryItem> referenceProfiles = inventoryItemRepository.searchByNameAndSku(request.getName(), request.getSku(), pageable);

    List<InventoryItemResponse> bearingResponses = referenceProfiles.getContent().stream().map(inventoryItemMapper::mapInventoryItemResponse).collect(Collectors.toList());

    return new PageImpl<>(bearingResponses, pageRequest, referenceProfiles.getTotalElements());
  }

  @Override
  public void create(InventoryItemRequest request) {
    inventoryItemRepository.save(inventoryItemMapper.mapInventoryItem(request));
  }

  @Override
  public void update(Long id, UpdateInventoryItemRequest request) {
    InventoryItem category = inventoryItemRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            ErrorCode.INVENTORY_ITEM_NOT_FOUND.toString()));

    inventoryItemMapper.update(request, category);
  }

  @Override
  public void delete(Long id) {
    try {
      inventoryItemRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.INVENTORY_ITEM_NOT_FOUND.toString());
    }
  }

  @Override
  public InventoryItemResponse findById(Long id) {
    return inventoryItemMapper.mapInventoryItemResponse(inventoryItemRepository.findById(id).orElseThrow(() -> new ApiRequestException(ErrorCode.INVENTORY_ITEM_NOT_FOUND)));
  }
}
