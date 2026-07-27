package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;

import com.nitan.agenticai.assistant.RefundAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AgenticTest {

  @Autowired
  private RefundAssistant refundAssistant;

  @Test
  void test() {
    String message = "Hi, I'd like a refund for my order 4471.";
    prettyPrint("User", message);
    prettyPrint("Assistant", refundAssistant.handleRefundRequest(message).approved() ?
        "Approved" : "Denied");
  }
}