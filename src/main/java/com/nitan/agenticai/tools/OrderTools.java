package com.nitan.agenticai.tools;

import dev.langchain4j.agent.tool.Tool;

public class OrderTools {

  @Tool("Returns the order amount in EUR for a given order id")
  public double getOrderAmount(String orderId) {
    System.out.println("Fetching order amount for order id: " + orderId);
    return 4200.0;
  }
}