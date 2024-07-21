package com.ms.library.svc.exception;

import com.ms.library.svc.model.enums.LibraryExceptionEnum;

public class GenericBadException extends RuntimeException {

  private final String errorCode;

  public GenericBadException(LibraryExceptionEnum exceptionEnum) {
    super(exceptionEnum.getErrorDescription());
    this.errorCode = exceptionEnum.getErrorCode();
  }

  public String getErrorCode() {
    return errorCode;
  }
}