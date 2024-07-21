package com.ms.library.svc.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BorrowerRequestDTO {

  public String name;
  public String email;

}
