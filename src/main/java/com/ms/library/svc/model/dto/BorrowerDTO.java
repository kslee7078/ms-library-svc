package com.ms.library.svc.model.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowerDTO implements Serializable {

  private UUID id;
  private String borrowerId;
  private String name;
  private String email;
  private String status;
  private int version;
  private String createdBy;
  private OffsetDateTime createdTime;
  private String lastUpdatedBy;
  private OffsetDateTime lastUpdatedTime;

}
