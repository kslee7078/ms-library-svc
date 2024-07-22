package com.ms.library.svc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.ms.library.svc.model.dto.BookRequestDTO;
import com.ms.library.svc.model.dto.BookResponseDTO;
import com.ms.library.svc.model.enums.BorrowedStatus;
import com.ms.library.svc.repository.BookCopyRepository;
import com.ms.library.svc.repository.BookRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

  @InjectMocks
  BookService bookService;

  @Mock
  BookRepository bookRepository;

  @Mock
  BookCopyRepository bookCopyRepository;

  private BookRequestDTO bookRequestDTO;
  private BookResponseDTO bookResponseDTO;

  @BeforeEach
  void setUp() {
    bookRequestDTO = new BookRequestDTO();
    bookResponseDTO = new BookResponseDTO();
  }

  @Test
  void registerBook_thenSuccess() {

    bookRequestDTO = BookRequestDTO.builder()
        .isbn("12345678")
        .author("John Wick")
        .title("John Wick V")
        .build();

    when(bookRepository.findByIsbn("12345678")).thenReturn(Optional.empty());

    bookResponseDTO = bookService.registerBook(bookRequestDTO);
    assertEquals(BorrowedStatus.RETURNED.getStatusCode(), (bookResponseDTO.getBorrowedStatus()));

  }

  @Test
  void bookResponseDTOPageable_thenSuccess() {
    List<BookResponseDTO> bookResponseDTOList = Collections.singletonList(BookResponseDTO.builder()
        .isbn("12345678")
        .author("John Wick")
        .title("John Wick V")
        .build());

    Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "title"));
    Page<BookResponseDTO> bookResponseDTOS = new PageImpl<>(bookResponseDTOList, pageable, bookResponseDTOList.size());

    when(bookCopyRepository.findBookCopies("", "", "", "", pageable)).thenReturn(bookResponseDTOS);

    bookResponseDTOS = bookService.bookResponseDTOPageable("", "", "", "", pageable);
    assertEquals(1, bookResponseDTOS.getTotalElements());
  }

}