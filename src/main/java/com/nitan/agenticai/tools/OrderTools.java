package com.nitan.agenticai.tools;

import dev.langchain4j.agent.tool.Tool;

public class OrderTools {

  @Tool("Returns the order amount in EUR for a given order id")
  double getOrderAmount(String orderId) {
    return 4200.0;
  }
}