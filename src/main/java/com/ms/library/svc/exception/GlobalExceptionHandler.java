package com.ms.library.svc.exception;

import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(GenericBadException.class)
  @ResponseBody
  public ResponseEntity<HashMap<String, String>> handleGenericBadException(GenericBadException ex) {
    HashMap<String, String> response = new HashMap<>();
    response.put("errorCode", ex.getErrorCode());
    response.put("errorDescription", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
