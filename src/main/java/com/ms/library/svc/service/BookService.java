package com.ms.library.svc.service;

import com.ms.library.svc.constant.CommonConstant;
import com.ms.library.svc.exception.GenericBadException;
import com.ms.library.svc.model.dto.BookCopyRequestDTO;
import com.ms.library.svc.model.dto.BookRequestDTO;
import com.ms.library.svc.model.dto.BookResponseDTO;
import com.ms.library.svc.model.entity.Book;
import com.ms.library.svc.model.entity.BookCopy;
import com.ms.library.svc.model.entity.Borrower;
import com.ms.library.svc.model.enums.BorrowedStatus;
import com.ms.library.svc.model.enums.LibraryExceptionEnum;
import com.ms.library.svc.model.enums.StatusCode;
import com.ms.library.svc.repository.BookCopyRepository;
import com.ms.library.svc.repository.BookRepository;
import com.ms.library.svc.repository.BorrowerRepository;
import com.ms.library.svc.utils.CommonUtils;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@AllArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final BookCopyRepository bookCopyRepository;
  private final BorrowerRepository borrowerRepository;

  @Transactional
  public BookResponseDTO registerBook(BookRequestDTO bookRequestDTO) {
    if (ObjectUtils.isEmpty(bookRequestDTO.getIsbn()) || ObjectUtils.isEmpty(bookRequestDTO.getTitle())
        || ObjectUtils.isEmpty(bookRequestDTO.getAuthor())) {
      log.info("[registerBook] hit error, mandatory field is not complete, {}", bookRequestDTO);
      throw new GenericBadException(LibraryExceptionEnum.L00001);
    }

    Book book = getBook(bookRequestDTO);
    BookCopy bookCopy = new BookCopy();
    bookCopy.setId(UUID.randomUUID());
    bookCopy.setBookCode(generateNextBookCode());
    bookCopy.setBook(book);
    bookCopy.setBorrowedStatus(BorrowedStatus.RETURNED.getStatusCode());
    bookCopy.setStatus(StatusCode.ACTIVE.getStatusCode());
    bookCopy.setCreatedBy(CommonConstant.ADMIN);
    bookCopy.setCreatedTime(OffsetDateTime.now());
    bookCopy.setLastUpdatedBy(CommonConstant.ADMIN);
    bookCopy.setLastUpdatedTime(OffsetDateTime.now());
    bookCopyRepository.saveAndFlush(bookCopy);

    return BookResponseDTO.builder()
        .isbn(book.getIsbn())
        .title(book.getTitle())
        .author(book.getAuthor())
        .bookCode(bookCopy.getBookCode())
        .borrowedStatus(bookCopy.getBorrowedStatus())
        .build();
  }

  private Book getBook(BookRequestDTO bookRequestDTO) {
    Book book = new Book();
    Optional<Book> bookOptional = bookRepository.findByIsbn(bookRequestDTO.getIsbn());
    if (bookOptional.isPresent()) {
      book = bookOptional.get();
    } else {
      book.setId(UUID.randomUUID());
      book.setIsbn(bookRequestDTO.getIsbn());
      book.setAuthor(bookRequestDTO.getAuthor());
      book.setTitle(bookRequestDTO.getTitle());
      book.setStatus(StatusCode.ACTIVE.getStatusCode());
      book.setCreatedBy(CommonConstant.ADMIN);
      book.setCreatedTime(OffsetDateTime.now());
      book.setLastUpdatedBy(CommonConstant.ADMIN);
      book.setLastUpdatedTime(OffsetDateTime.now());
      bookRepository.saveAndFlush(book);
    }
    return book;
  }

  public String generateNextBookCode() {
    String maxBorrowerId = bookCopyRepository.findMaxBookCode();
    String nextBorrowerId;

    if (maxBorrowerId == null) {
      nextBorrowerId = "BOOK0000001";
    } else {
      String numberPart = maxBorrowerId.substring(4);
      int nextNumber = Integer.parseInt(numberPart) + 1;
      nextBorrowerId = String.format("BOOK%07d", nextNumber);
    }

    return nextBorrowerId;
  }

  @Transactional
  public BookResponseDTO borrowBook(BookCopyRequestDTO bookCopyRequestDTO) {
    BookResponseDTO bookResponseDTO;

    if (ObjectUtils.isEmpty(bookCopyRequestDTO.getBookCode()) || ObjectUtils.isEmpty(bookCopyRequestDTO.getBorrowerId())) {
      log.info("[borrowBook] hit error, mandatory field is not complete, {}", bookCopyRequestDTO);
      throw new GenericBadException(LibraryExceptionEnum.L00001);
    }

    Optional<Borrower> borrowerOptional = borrowerRepository.findByBorrowerId(bookCopyRequestDTO.getBorrowerId());

    if (borrowerOptional.isEmpty()) {
      log.info("[borrowBook] User not found, {}", bookCopyRequestDTO.getBorrowerId());
      throw new GenericBadException(LibraryExceptionEnum.L00004);
    }

    Optional<BookCopy> bookCopyOptional = bookCopyRepository.findByBookCode(bookCopyRequestDTO.getBookCode());

    if (bookCopyOptional.isPresent()) {
      BookCopy bookCopy = bookCopyOptional.get();
      if (BorrowedStatus.BORROWED.getStatusCode().equals(bookCopy.getBorrowedStatus())) {
        log.info("[borrowBook] Book is not already borrowed by other user, {}", bookCopy.getBorrowerId());
        throw new GenericBadException(LibraryExceptionEnum.L00005);
      }
      bookCopy.setBorrowedStatus(BorrowedStatus.BORROWED.getStatusCode());
      bookCopy.setBorrowerId(bookCopyRequestDTO.getBorrowerId());
      bookCopy.setLastBorrowedDate(OffsetDateTime.now());
      bookCopy.setReturningDueDate(CommonUtils.getEndOfDayInFuture(5));
      bookCopy.setLastUpdatedTime(OffsetDateTime.now());
      bookCopyRepository.save(bookCopy);

      bookResponseDTO = BookResponseDTO.builder()
          .isbn(bookCopy.getBook().getIsbn())
          .title(bookCopy.getBook().getTitle())
          .author(bookCopy.getBook().getAuthor())
          .borrowedStatus(bookCopy.getBorrowedStatus())
          .returningDueDate(bookCopy.getReturningDueDate())
          .lastBorrowedDate(bookCopy.getLastBorrowedDate())
          .borrowerId(bookCopyRequestDTO.getBorrowerId())
          .bookCode(bookCopy.getBookCode())
          .build();

    } else {
      log.info("[borrowBook] Book is not found, {}", bookCopyRequestDTO);
      throw new GenericBadException(LibraryExceptionEnum.L00006);
    }

    return bookResponseDTO;
  }

  public BookResponseDTO returnBook(BookCopyRequestDTO bookCopyRequestDTO) {

    if (ObjectUtils.isEmpty(bookCopyRequestDTO.getBookCode())) {
      log.info("[returnBook] hit error, mandatory field is not complete, {}", bookCopyRequestDTO);
      throw new GenericBadException(LibraryExceptionEnum.L00001);
    }

    BookResponseDTO bookResponseDTO;

    Optional<BookCopy> bookCopyOptional = bookCopyRepository.findByBookCode(bookCopyRequestDTO.getBookCode());
    if (bookCopyOptional.isPresent()) {
      BookCopy bookCopy = bookCopyOptional.get();
      if (BorrowedStatus.RETURNED.getStatusCode().equals(bookCopy.getBorrowedStatus())) {
        log.info("[returnBook] hit error, book already returned, {}", bookCopyRequestDTO);
        throw new GenericBadException(LibraryExceptionEnum.L00007);
      }
      bookCopy.setBorrowedStatus(BorrowedStatus.RETURNED.getStatusCode());
      bookCopy.setReturningDueDate(null);
      bookCopy.setLastReturnedDate(OffsetDateTime.now());
      bookCopy.setLastUpdatedBy(CommonConstant.ADMIN);
      bookCopy.setLastUpdatedTime(OffsetDateTime.now());
      bookCopy.setBorrowerId(null);
      bookCopyRepository.save(bookCopy);

      bookResponseDTO = BookResponseDTO.builder()
          .isbn(bookCopy.getBook().getIsbn())
          .title(bookCopy.getBook().getTitle())
          .author(bookCopy.getBook().getAuthor())
          .borrowedStatus(bookCopy.getBorrowedStatus())
          .lastReturnedDate(bookCopy.getLastReturnedDate())
          .returningDueDate(bookCopy.getReturningDueDate())
          .lastBorrowedDate(bookCopy.getLastBorrowedDate())
          .borrowerId(bookCopyRequestDTO.getBorrowerId())
          .bookCode(bookCopy.getBookCode())
          .build();
    } else {
      log.info("[returnBook] hit error, book is not found, {}", bookCopyRequestDTO);
      throw new GenericBadException(LibraryExceptionEnum.L00006);
    }

    return bookResponseDTO;
  }

  public Page<BookResponseDTO> bookResponseDTOPageable(String bookCode, String isbn, String title, String author, Pageable pageable) {
   return bookCopyRepository.findBookCopies(bookCode, isbn,
        title, author, pageable);
  }


}
