package com.ms.library.svc.repository;

import com.ms.library.svc.model.entity.Book;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

  Optional<Book> findByIsbn(String isbn);

}
