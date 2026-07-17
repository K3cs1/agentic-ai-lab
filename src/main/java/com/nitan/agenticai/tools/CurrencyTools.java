package com.nitan.agenticai.tools;

import com.nitan.agenticai.util.AiTool;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CurrencyTools implements AiTool {

  @Tool("Get the current exchange rate between two currency codes, e.g. USD to EUR.")
  public double getExchangeRate(String fromCurrency, String toCurrency) {
    log.info("Fetching exchange rate {} -> {}", fromCurrency, toCurrency);
    if ("USD".equalsIgnoreCase(fromCurrency) && "EUR".equalsIgnoreCase(toCurrency)) {
      return 0.87;
    }
    return 1.0;
  }
}