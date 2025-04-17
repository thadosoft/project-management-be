package com.example.projectmanagementbe.api.services.impls.inventory;

import com.example.projectmanagementbe.api.mappers.inventory.InventoryTransactionMapper;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryTransactionRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryTransactionRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryTransactionResponse;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.SearchInventoryTransactionRequest;
import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
import com.example.projectmanagementbe.api.models.iventory.InventoryTransaction;
import com.example.projectmanagementbe.api.repositories.inventory.InventoryItemRepository;
import com.example.projectmanagementbe.api.repositories.inventory.InventoryTransactionRepository;
import com.example.projectmanagementbe.api.services.inventory.IInventoryTransactionService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class InventoryTransactionServiceImple implements IInventoryTransactionService {

  private final InventoryTransactionRepository inventoryTransactionRepository;
  private final InventoryTransactionMapper transactionMapper;
  private final InventoryItemRepository inventoryItemRepository;

  @Override
  public List<InventoryTransactionResponse> findAll() {
    return inventoryTransactionRepository.findAll().stream().map(transactionMapper::mapInventoryTransactionResponse).toList();
  }

  @Override
  @Transactional
  public void create(InventoryTransactionRequest transaction) {
    InventoryItem inventory = inventoryItemRepository.findById(transaction.getItemId())
        .orElseThrow(() ->new ApiRequestException(ErrorCode.INVENTORY_ITEM_NOT_FOUND));

    if ("NK".equalsIgnoreCase(transaction.getTransactionType()) || "XK".equalsIgnoreCase(transaction.getTransactionType())) {
      if (inventory.getQuantityInStock() < transaction.getQuantity()) {
        throw new ApiRequestException(ErrorCode.NOT_ENOUGH_STOCK);
      }
      inventory.setQuantityInStock(inventory.getQuantityInStock() - transaction.getQuantity());
    } else if ("IMPORT".equalsIgnoreCase(transaction.getTransactionType()) || "IN".equalsIgnoreCase(transaction.getTransactionType())) {
      inventory.setQuantityInStock(inventory.getQuantityInStock() + transaction.getQuantity());
    } else {
      throw new ApiRequestException(ErrorCode.INVALID_TRANSACTION_TYPE);
    }

    inventory.setStatus(inventory.getQuantityInStock() == 0 ? "Out of stock" : "In stock");

    inventoryTransactionRepository.save(transactionMapper.mapInventoryTransaction(transaction));
    inventoryItemRepository.save(inventory);
  }

  @Override
  @Transactional
  public void update(Long id, UpdateInventoryTransactionRequest inventoryTransactionRequest) {
    InventoryTransaction transaction = inventoryTransactionRepository.findById(id)
        .orElseThrow(() -> new ApiRequestException(ErrorCode.INVENTORY_TRANSACTION_NOT_FOUND));

    InventoryItem oldInventory = transaction.getItem();
    InventoryItem newInventory = inventoryItemRepository.findById(inventoryTransactionRequest.getItemId())
        .orElseThrow(() -> new ApiRequestException(ErrorCode.INVENTORY_ITEM_NOT_FOUND));

    int oldQuantity = transaction.getQuantity();
    int newQuantity = inventoryTransactionRequest.getQuantity();

    if (!oldInventory.getId().equals(newInventory.getId())) {
      oldInventory.setQuantityInStock(oldInventory.getQuantityInStock() + oldQuantity);
      inventoryItemRepository.save(oldInventory);

      if (newInventory.getQuantityInStock() < newQuantity) {
        throw new ApiRequestException(ErrorCode.NOT_ENOUGH_STOCK);
      }
      newInventory.setQuantityInStock(newInventory.getQuantityInStock() - newQuantity);
    } else {
      int diff = newQuantity - oldQuantity;
      if (diff > 0 && newInventory.getQuantityInStock() < diff) {
        throw new ApiRequestException(ErrorCode.NOT_ENOUGH_STOCK);
      }
      newInventory.setQuantityInStock(newInventory.getQuantityInStock() - diff);
    }

    newInventory.setStatus(newInventory.getQuantityInStock() == 0 ? "Out of stock" : "In stock");

    transactionMapper.update(inventoryTransactionRequest, transaction);
    transaction.setItem(newInventory);

    inventoryItemRepository.save(newInventory);
    inventoryTransactionRepository.save(transaction);
  }

  @Override
  public void delete(Long id) {
    try {
      inventoryTransactionRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.INVENTORY_TRANSACTION_NOT_FOUND.toString());
    }
  }

  @Override
  public InventoryTransactionResponse findById(Long id) {
    return transactionMapper.mapInventoryTransactionResponse(inventoryTransactionRepository.findById(id).orElseThrow(() -> new ApiRequestException(ErrorCode.INVENTORY_TRANSACTION_NOT_FOUND)));
  }

  @Override
  public Page<InventoryTransactionResponse> searchByParams(SearchInventoryTransactionRequest request, Pageable pageable) {

    Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));

    Page<InventoryTransaction> referenceProfiles = inventoryTransactionRepository.searchByParams(request.getTransactionType(), request.getReceiver(), request.getProcessedBy(), pageable);

    List<InventoryTransactionResponse> bearingResponses = referenceProfiles.getContent().stream().map(transactionMapper::mapInventoryTransactionResponse).collect(Collectors.toList());

    return new PageImpl<>(bearingResponses, pageRequest, referenceProfiles.getTotalElements());
  }
}
