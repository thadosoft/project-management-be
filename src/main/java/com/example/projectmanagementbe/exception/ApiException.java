package com.example.projectmanagementbe.exception;

import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class ApiException {
  private final String message;
  private final int httpCode;
  private final ZonedDateTime timestamp;
}
