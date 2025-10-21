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
  EMPLOYEE_NOT_FOUND("011", "Employee not found"),
  EXPORT_REPORT_FAILED("012", "Export report failed"),
  CONTRACT_NOT_FOUND("013", " Contract not found"),
  QUOTATION_NOT_FOUND("014", " Quotation not found"),
  ATTENDANCE_NOT_FOUND("015", " Attendance not found"),
  NOT_ENOUGH_STOCK("016", " Not enough stock "),
  INVALID_TRANSACTION_TYPE("017", " Invalid transaction type "),
  FILE_EMPTY("018", " File empty "),
  FILE_UPLOAD_FAILED("019", " File upload failed "),
  FILE_NOT_FOUND("020", " File not found "),
  LEAVE_REQUEST_NOT_FOUND("021", "Leave request not found"),
  WHITE_BOARD_NOT_FOUND("022", "White board not found"),
  EMPLOYEE_OF_MONTH_NOT_FOUND("023", "Employee of Month not found"),
  BOOK_NOT_FOUND("030", "Book not found"),
  BOOK_NOT_AVAILABLE("031", "Book is not available for borrowing"),
  BOOK_LIMIT_EXCEEDED("032", "User has reached the maximum number of borrowed books"),
  BOOK_ALREADY_RETURNED("033", "Book has already been returned"),
  BOOK_LOAN_NOT_FOUND("034", "Book loan record not found"),
  BOOK_OVERDUE("035", "Book loan is overdue"),
  BOOK_LOAN_ACTIVE_DELETE_DENIED("036", "Cannot delete a record that is currently borrowed. Please return the book first."),
  BOOK_ALREADY_EXISTS("037", "Book with the same title already exists"),
          ;

  private final String code;

  private final String message;
}
