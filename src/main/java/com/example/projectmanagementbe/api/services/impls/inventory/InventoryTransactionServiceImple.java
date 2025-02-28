package com.example.projectmanagementbe.api.services.impls.inventory;

import com.example.projectmanagementbe.api.mappers.inventory.InventoryTransactionMapper;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Create.InventoryTransactionRequest;
import com.example.projectmanagementbe.api.models.dto.requests.inventory.Update.UpdateInventoryTransactionRequest;
import com.example.projectmanagementbe.api.models.dto.responses.inventory.InventoryTransactionResponse;
import com.example.projectmanagementbe.api.models.iventory.InventoryTransaction;
import com.example.projectmanagementbe.api.repositories.inventory.InventoryTransactionRepository;
import com.example.projectmanagementbe.api.services.inventory.IInventoryTransactionService;
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
public class InventoryTransactionServiceImple implements IInventoryTransactionService {

  private final InventoryTransactionRepository inventoryTransactionRepository;
  private final InventoryTransactionMapper transactionMapper;

  @Override
  public List<InventoryTransactionResponse> findAll() {
    return inventoryTransactionRepository.findAll().stream().map(transactionMapper::mapInventoryTransaction).toList();
  }

  @Override
  public void create(InventoryTransactionRequest inventoryTransactionRequest) {
    inventoryTransactionRepository.save(transactionMapper.mapInventoryTransaction(inventoryTransactionRequest));
  }

  @Override
  @Transactional
  public void update(Long id, UpdateInventoryTransactionRequest inventoryTransactionRequest) {
    InventoryTransaction Transaction = inventoryTransactionRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            ErrorCode.INVENTORY_TRANSACTION_NOT_FOUND.toString()));

    transactionMapper.update(inventoryTransactionRequest, Transaction);
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
    return transactionMapper.mapInventoryTransaction(inventoryTransactionRepository.findById(id).orElseThrow(() -> new ApiRequestException(ErrorCode.INVENTORY_TRANSACTION_NOT_FOUND)));
  }
}
