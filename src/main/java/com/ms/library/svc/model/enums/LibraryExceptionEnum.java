package com.ms.library.svc.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LibraryExceptionEnum {
  L00001("L00001", "Fields are mandatory"),
  L00002("L00002", "Invalid Email Address"),
  L00003("L00003", "User Email Address already registered"),
  L00004("L00004", "User not found"),
  L00005("L00005", "Book borrowed by other user"),
  L00006("L00006", "Book is not found"),
  L00007("L00007", "Book already returned"),
  L99999("L99999", "Exception occurred. Please contact customer service"),;

  private final String errorCode;
  private final String errorDescription;
}
