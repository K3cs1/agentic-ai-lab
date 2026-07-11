package com.nitan.agenticai.tools;

import dev.langchain4j.agent.tool.ReturnBehavior;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CurrencyTools {

  @Tool(value = "Get the current exchange rate between two currency codes, e.g. USD to EUR.",
  returnBehavior = ReturnBehavior.IMMEDIATE)
  public double getExchangeRate(String fromCurrency, String toCurrency) {
    log.info("Fetching exchange rate {} -> {}", fromCurrency, toCurrency);
    // Fake but fixed rate so you can verify correctness deterministically
    if ("USD".equalsIgnoreCase(fromCurrency) && "EUR".equalsIgnoreCase(toCurrency)) {
      return 0.87;
    }
    return 1.0;
  }
}