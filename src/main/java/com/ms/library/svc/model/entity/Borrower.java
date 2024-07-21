package com.ms.library.svc.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "borrower")
@Getter
@Setter
public class Borrower implements Serializable {

  @Id
  private UUID id;

  @Column(name = "borrower_id")
  private String borrowerId;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

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

}
