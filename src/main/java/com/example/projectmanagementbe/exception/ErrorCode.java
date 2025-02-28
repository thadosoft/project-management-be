package com.example.projectmanagementbe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  UNKNOWN_ERROR("000", "Unknown error"),
  USER_NOT_FOUND("001", "User not found"),
  INVALID_CREDENTIAL("002", "Invalid credential"),
  UNAUTHORIZED("004", "Unauthorized"),
  USER_EXISTING("005", "User is existing"),
  ROLE_NOT_FOUND("006", "Role not found"),
  MODULE_NOT_FOUND("007", "Module not found"),
  REFERENCE_PROFILE_NOT_FOUND("008", "Reference profile not found"),
  INVENTORY_CATEGORY_NOT_FOUND("009", "Inventory category not found"),
  INVENTORY_ITEM_NOT_FOUND("010", "Inventory item not found"),
  INVENTORY_TRANSACTION_NOT_FOUND("010", "Inventory transaction item not found"),

  ;

  private final String code;

  private final String message;
}
