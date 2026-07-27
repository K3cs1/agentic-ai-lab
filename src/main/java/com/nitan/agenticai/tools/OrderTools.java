package com.nitan.agenticai.tools;

import dev.langchain4j.agent.tool.Tool;

public class OrderTools {

  @Tool("Returns the order requestedAmount in EUR for a given order id")
  double getOrderAmount(String orderId) {
    System.out.println("Fetching order amount for order id: " + orderId);
    return 50.5;
  }
}