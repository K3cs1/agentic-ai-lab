package com.nitan.agenticai.tools;

import dev.langchain4j.agent.tool.ReturnBehavior;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CurrencyTools {

  @Tool(value = "Get the current exchange rate between two currency codes, e.g. USD to EUR.")
  public double getExchangeRate(String fromCurrency, String toCurrency) {
    log.info("Fetching exchange rate {} -> {}", fromCurrency, toCurrency);
    // Fake but fixed rate so you can verify correctness deterministically
    if ("USD".equalsIgnoreCase(fromCurrency) && "EUR".equalsIgnoreCase(toCurrency)) {
      return 0.87;
    }
    return 1.0;
  }

  @Tool("Convert an amount using a given exchange rate.")
  public double convertAmount(double amount, double rate) {
    log.info("Converting {} using rate {}", amount, rate);
    return amount * rate;
  }
}