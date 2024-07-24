package com.ms.library.svc.controller;

import com.ms.library.svc.constant.ResourcePath;
import com.ms.library.svc.model.dto.BorrowerDTO;
import com.ms.library.svc.model.dto.BorrowerRequestDTO;
import com.ms.library.svc.service.BorrowerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ResourcePath.VERSION_V1)
@AllArgsConstructor
@Slf4j
public class BorrowerController {

  private final BorrowerService borrowerService;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = ResourcePath.GET_BORROWER_BY_STATUS)
  public ResponseEntity<Page<BorrowerDTO>> borrowerPageableByStatus(
      @PathVariable("status") String status,
      @RequestParam(defaultValue = "1") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "borrowerId") String sortBy,
      @RequestParam(defaultValue = "ASC") String orderBy
  ) {
    Sort.Direction direction = orderBy.toUpperCase().contains("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
    Pageable pageable = PageRequest.of(Math.max(pageNo - 1, 0), pageSize, Sort.by(direction, sortBy));
    return ResponseEntity.status(HttpStatus.OK).body(borrowerService.borrowerPageableByStatus(status, pageable));
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = ResourcePath.REGISTER_BORROWER)
  public ResponseEntity<BorrowerDTO> registerBorrower(
      @RequestBody BorrowerRequestDTO borrowerRequestDTO
  ) {
    return ResponseEntity.status(HttpStatus.OK).body(borrowerService.registerBorrower(borrowerRequestDTO));
  }

}
