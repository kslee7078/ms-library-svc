package com.ms.library.svc.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
  ACTIVE("A", "Active"),
  INACTIVE("I", "Inactive"),
  DELETED("D", "Deleted");

  private final String statusCode;
  private final String statusDescription;


}
