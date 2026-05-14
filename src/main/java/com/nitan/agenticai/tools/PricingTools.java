package com.nitan.agenticai.tools;

import com.nitan.agenticai.domain.ClientType;
import com.nitan.agenticai.domain.PricingResult;
import dev.langchain4j.agent.tool.Tool;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PricingTools {
  @Tool(
      "Calculates final invoice amount based on client type and total amount.It is MANDATORY to determine UPFRONT both the client type and the total amount.The client type can be resolved based on the client id.The total amount is the sum of all invoices for a the given client.")
  public PricingResult calculateDiscount(ClientType clientType, BigDecimal totalAmount) {
    log.info(
        "Calculating discount for client type: "
            + clientType
            + " and total amount: "
            + totalAmount);
    BigDecimal discountAmount = BigDecimal.ZERO;
    String reason = "No discount";

    switch (clientType) {
      case GOLD:
        discountAmount = totalAmount.multiply(BigDecimal.valueOf(0.20));
        reason = "20% discount for GOLD clients";
        break;
      case SILVER:
        discountAmount = totalAmount.multiply(BigDecimal.valueOf(0.10));
        reason = "10% discount for SILVER clients";
        break;
      case BRONZE:
        discountAmount = totalAmount.multiply(BigDecimal.valueOf(0.05));
        reason = "5% discount for BRONZE clients";
        break;
    }

    BigDecimal finalAmount = totalAmount.subtract(discountAmount);
    return new PricingResult(discountAmount, finalAmount, reason);
  }
}
