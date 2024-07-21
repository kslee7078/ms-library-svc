package com.ms.library.svc.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "book_copy")
@Getter
@Setter
public class BookCopy implements Serializable {

  @Id
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
  private Book book;

  @Column(name = "borrowed_status")
  private String borrowedStatus;

  @Column(name = "last_borrowed_date")
  private OffsetDateTime lastBorrowedDate;

  @Column(name = "last_returned_date")
  private OffsetDateTime lastReturnedDate;

  @Column(name = "returning_due_date")
  private OffsetDateTime returningDueDate;

  @Column(name = "borrower_id")
  private String borrowerId;

  @Column(name = "sts_cd")
  private String status;

  @Version
  @Column(name = "ver_nbr")
  private int version;

  @Column(name = "crt_by")
  private String createdBy;

  @Column(name = "crt_time")
  private OffsetDateTime createdTime;

  @Column(name = "upd_by")
  private String lastUpdatedBy;

  @Column(name = "upd_time")
  private OffsetDateTime lastUpdatedTime;

  @Column(name = "book_code")
  private String bookCode;

}
