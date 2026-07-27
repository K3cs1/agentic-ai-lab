package com.nitan.agenticai.tools;

import dev.langchain4j.agent.tool.Tool;

public class OrderTools {

  @Tool("Returns the order requestedAmount in EUR for a given order id")
  public double getOrderAmount(String orderId) {
    return 50.9;
  }
}