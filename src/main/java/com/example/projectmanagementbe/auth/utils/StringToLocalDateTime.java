package com.example.projectmanagementbe.auth.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateTime {
  public static LocalDateTime parseDateToLocalDateTime(String dateStr, boolean isEndDate) {
    if (dateStr == null || dateStr.isEmpty()) {
      return null;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(dateStr, formatter);

    if (isEndDate) {
      return localDate.atTime(23, 59, 59);
    } else {
      return localDate.atStartOfDay();
    }
  }
}
