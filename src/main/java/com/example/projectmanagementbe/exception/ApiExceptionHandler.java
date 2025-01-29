package com.example.projectmanagementbe.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(ApiRequestException.class)
  public ResponseEntity<?> handleException(ApiRequestException exception) {
    HttpStatus badRequest = HttpStatus.BAD_REQUEST;

    ApiException apiException = new ApiException(
        exception.getMessage(),
        badRequest.value(),
        ZonedDateTime.now(ZoneId.of("+07:00"))
    );

    return new ResponseEntity<>(apiException, badRequest);
  }
}
