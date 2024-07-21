package com.ms.library.svc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.library.svc.constant.CommonConstant;
import com.ms.library.svc.exception.GenericBadException;
import com.ms.library.svc.model.dto.BorrowerDTO;
import com.ms.library.svc.model.dto.BorrowerRequestDTO;
import com.ms.library.svc.model.entity.Borrower;
import com.ms.library.svc.model.enums.LibraryExceptionEnum;
import com.ms.library.svc.model.enums.StatusCode;
import com.ms.library.svc.repository.BorrowerRepository;
import com.ms.library.svc.utils.CommonUtils;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class BorrowerService {

  private final BorrowerRepository borrowerRepository;
  private final ObjectMapper objectMapper;

  public Page<BorrowerDTO> borrowerPageableByStatus(String status, Pageable pageable) {
    Page<Borrower> borrowers = borrowerRepository.findByStatus(status, pageable);
    return borrowers.map(borrower -> objectMapper.convertValue(borrower, BorrowerDTO.class));
  }

  @Transactional
  public BorrowerDTO registerBorrower(BorrowerRequestDTO borrowerRequestDTO) {
    if (Objects.isNull(borrowerRequestDTO.getName()) || Objects.isNull(borrowerRequestDTO.getEmail())) {
      log.info("[registerBorrower] hit error, mandatory field is not complete, {}", borrowerRequestDTO);
      throw new GenericBadException(LibraryExceptionEnum.L00001);
    }

    if (!CommonUtils.isValidEmail(borrowerRequestDTO.getEmail())) {
      log.info("[registerBorrower] hit error, email entered is not valid, {}", borrowerRequestDTO.getEmail());
      throw new GenericBadException(LibraryExceptionEnum.L00002);
    }

    if (borrowerRepository.findByEmail(borrowerRequestDTO.getEmail()).isPresent()) {
      log.info("[registerBorrower] hit error, user email already registered, {}", borrowerRequestDTO.getEmail());
      throw new GenericBadException(LibraryExceptionEnum.L00003);
    }

    Borrower borrower = new Borrower();
    borrower.setId(UUID.randomUUID());
    borrower.setName(borrowerRequestDTO.getName());
    borrower.setEmail(borrowerRequestDTO.getEmail());
    borrower.setBorrowerId(generateNextBorrowerId());
    borrower.setStatus(StatusCode.ACTIVE.getStatusCode());
    borrower.setCreatedBy(CommonConstant.ADMIN);            //temporary hardcode as admin
    borrower.setLastUpdatedBy(CommonConstant.ADMIN);        //temporary hardcode as admin
    borrower.setCreatedTime(OffsetDateTime.now());
    borrower.setLastUpdatedTime(OffsetDateTime.now());
    borrowerRepository.saveAndFlush(borrower);

    return objectMapper.convertValue(borrower, BorrowerDTO.class);
  }


  public String generateNextBorrowerId() {
    String maxBorrowerId = borrowerRepository.findMaxBorrowerId();
    String nextBorrowerId;

    if (maxBorrowerId == null) {
      nextBorrowerId = "B00001";
    } else {
      String numberPart = maxBorrowerId.substring(1);
      int nextNumber = Integer.parseInt(numberPart) + 1;
      nextBorrowerId = String.format("B%05d", nextNumber);
    }

    return nextBorrowerId;
  }

}
