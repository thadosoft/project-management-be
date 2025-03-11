package com.example.projectmanagementbe.auth.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

  public static LocalDateTime parseDateToLocalDateTime2(String dateStr, boolean isEndDate) {
    if (dateStr == null || dateStr.isEmpty()) {
      return null;
    }

    try {
      // TH1: Nếu chuỗi có định dạng đầy đủ yyyy-MM-dd HH:mm:ss.SSS
      DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
      LocalDateTime dateTime = LocalDateTime.parse(dateStr, fullFormatter);
      return isEndDate ? dateTime.withHour(23).withMinute(59).withSecond(59).withNano(0)
          : dateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
    } catch (DateTimeParseException e) {
      // TH2: Nếu chuỗi chỉ có yyyy-MM-dd
      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate localDate = LocalDate.parse(dateStr, dateFormatter);
      return isEndDate ? localDate.atTime(23, 59, 59) : localDate.atStartOfDay();
    }
  }
}
