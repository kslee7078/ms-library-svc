package com.ms.library.svc.model.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDTO {

  private String isbn;
  private String title;
  private String author;
  private String borrowedStatus;
  private OffsetDateTime lastBorrowedDate;
  private OffsetDateTime lastReturnedDate;
  private OffsetDateTime returningDueDate;
  private Serializable borrowerId;
  private String bookCode;


}
