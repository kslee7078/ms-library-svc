package com.ms.library.svc.controller;

import com.ms.library.svc.constant.ResourcePath;
import com.ms.library.svc.model.dto.BookCopyRequestDTO;
import com.ms.library.svc.model.dto.BookRequestDTO;
import com.ms.library.svc.model.dto.BookResponseDTO;
import com.ms.library.svc.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
public class BookController {

  private final BookService bookService;

  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = ResourcePath.REGISTER_BOOK)
  public ResponseEntity<BookResponseDTO> registerBorrower(
      @RequestBody BookRequestDTO bookRequestDTO
  ) {
    return ResponseEntity.status(HttpStatus.OK).body(bookService.registerBook(bookRequestDTO));
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = ResourcePath.BORROW_BOOK)
  public ResponseEntity<BookResponseDTO> borrowBook(
      @RequestBody BookCopyRequestDTO bookCopyRequestDTO
  ) {
    return ResponseEntity.status(HttpStatus.OK).body(bookService.borrowBook(bookCopyRequestDTO));
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = ResourcePath.RETURN_BOOK)
  public ResponseEntity<BookResponseDTO> returnBook(
      @RequestBody BookCopyRequestDTO bookCopyRequestDTO
  ) {
    return ResponseEntity.status(HttpStatus.OK).body(bookService.returnBook(bookCopyRequestDTO));
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = ResourcePath.GET_BOOK_PAGEABLE)
  public ResponseEntity<Page<BookResponseDTO>> bookResponseDTOPageable(
      @RequestParam(defaultValue = "") String bookCode,
      @RequestParam(defaultValue = "") String isbn,
      @RequestParam(defaultValue = "") String title,
      @RequestParam(defaultValue = "") String author,
      @RequestParam(defaultValue = "1") Integer pageNo,
      @RequestParam(defaultValue = "2") Integer pageSize,
      @RequestParam(defaultValue = "bookCode") String sortBy,
      @RequestParam(defaultValue = "ASC") String orderBy
  ) {
    Sort.Direction direction = orderBy.toUpperCase().contains("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;

    if (sortBy.equalsIgnoreCase("title") || sortBy.equalsIgnoreCase("isbn") || sortBy.equalsIgnoreCase("author")) {
      sortBy = "book." + sortBy;
    }

    Pageable pageable = PageRequest.of(Math.max(pageNo - 1, 0), pageSize, Sort.by(direction, sortBy));
    return ResponseEntity.status(HttpStatus.OK).body(bookService.bookResponseDTOPageable(bookCode, isbn, title, author, pageable));
  }

}
