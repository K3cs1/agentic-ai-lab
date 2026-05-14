package com.nitan.agenticai.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {
  private Long id;
  private String name;
  private Address address;
}
