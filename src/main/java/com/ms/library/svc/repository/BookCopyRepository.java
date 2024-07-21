package com.ms.library.svc.repository;

import com.ms.library.svc.model.dto.BookResponseDTO;
import com.ms.library.svc.model.entity.BookCopy;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookCopyRepository extends JpaRepository<BookCopy, UUID> {

  @Query("SELECT MAX(b.bookCode) FROM BookCopy b")
  String findMaxBookCode();

  Optional<BookCopy> findByBookCode(String bookCode);

  @Query("SELECT new com.ms.library.svc.model.dto.BookResponseDTO("
      + "b.isbn, b.title, b.author, bc.borrowedStatus, "
      + "bc.lastBorrowedDate, bc.lastReturnedDate, "
      + "bc.returningDueDate, bc.borrowerId, bc.bookCode) "
      + "FROM BookCopy bc "
      + "JOIN bc.book b "
      + "WHERE (:bookCode IS NULL OR bc.bookCode LIKE %:bookCode%) AND "
      + "(:isbn IS NULL OR b.isbn LIKE %:isbn%) AND "
      + "(:title IS NULL OR b.title LIKE %:title%) AND "
      + "(:author IS NULL OR b.author LIKE %:author%)")
  Page<BookResponseDTO> findBookCopies(
      @Param("bookCode") String bookCode,
      @Param("isbn") String isbn,
      @Param("title") String title,
      @Param("author") String author,
      Pageable pageable);

}
