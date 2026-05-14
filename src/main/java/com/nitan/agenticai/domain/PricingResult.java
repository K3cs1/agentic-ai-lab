package com.nitan.agenticai.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PricingResult {
  private BigDecimal discountAmount;
  private BigDecimal finalAmount;
  private String reason;
}
