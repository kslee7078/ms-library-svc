package com.ms.library.svc.repository;

import com.ms.library.svc.model.entity.Borrower;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BorrowerRepository extends JpaRepository<Borrower, UUID> {

  Page<Borrower> findByStatus(String status, Pageable pageable);

  @Query("SELECT MAX(b.borrowerId) FROM Borrower b")
  String findMaxBorrowerId();

  Optional<Borrower> findByEmail(String email);

  Optional<Borrower> findByBorrowerId(String borrowerId);


}
