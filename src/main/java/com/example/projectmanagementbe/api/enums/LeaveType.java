package com.example.projectmanagementbe.api.enums;

import lombok.Getter;

/**
 * Only ANNUAL leave draws down the yearly balance for now. The remaining
 * types are kept so the feature can grow without another migration.
 */
@Getter
public enum LeaveType {
  ANNUAL(true),
  UNPAID(false),
  SICK(false),
  OTHER(false);

  private final boolean deductsBalance;

  LeaveType(boolean deductsBalance) {
    this.deductsBalance = deductsBalance;
  }

  public static LeaveType fromNullable(String raw) {
    if (raw == null || raw.isBlank()) {
      return ANNUAL;
    }
    try {
      return LeaveType.valueOf(raw.trim().toUpperCase());
    } catch (IllegalArgumentException ex) {
      return ANNUAL;
    }
  }
}
