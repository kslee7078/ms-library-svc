package com.ms.library.svc.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "book")
@Getter
@Setter
public class Book implements Serializable {

  @Id
  private UUID id;

  @Column(name = "isbn")
  private String isbn;

  @Column(name = "title")
  private String title;

  @Column(name = "author")
  private String author;

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
