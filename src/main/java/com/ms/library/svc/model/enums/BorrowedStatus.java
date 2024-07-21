package com.ms.library.svc.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BorrowedStatus {
  BORROWED("B", "Borrowed"),
  RETURNED("R", "Returned");

  private final String statusCode;
  private final String statusDescription;
}
