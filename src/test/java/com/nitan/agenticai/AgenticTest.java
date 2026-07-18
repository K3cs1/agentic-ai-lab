package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;

import com.nitan.agenticai.assistant.AgenticAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AgenticTest {

  @Autowired
  AgenticAssistant chatAssistant;

  @Test
  void test() {
    String message = "What's the BTC to USD exchange rate?";
    prettyPrint("User", message);
    while (true) {
      prettyPrint("Assistant", chatAssistant.handle(message));
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
