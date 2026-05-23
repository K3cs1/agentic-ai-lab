package com.nitan.agenticai.tools;

import static com.nitan.agenticai.util.Util.prettyPrint;

import dev.langchain4j.agent.tool.Tool;
import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BilingTools {
  @Tool("Get all invoices for a client's id.")
  public List<BigDecimal> getInvoices(Long clientId) {
    prettyPrint("getInvoices","Fetching invoices for client: "+ clientId);

    if (clientId == 1L) {
      return List.of(new BigDecimal("5000"), new BigDecimal("7500"), new BigDecimal("2000"));
    }

    if (clientId == 2L) {
      return List.of(new BigDecimal("10000"), new BigDecimal("2500"));
    }

    throw new IllegalArgumentException("Unknown client: " + clientId);
  }
}
