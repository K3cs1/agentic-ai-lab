package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;

import com.nitan.agenticai.assistant.AgenticAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AgenticTest {

  @Autowired
  private AgenticAssistant agenticAssistant;

  @Test
  void test() throws InterruptedException {

      String message = "What is the exchange rate from USD to EUR?";
      prettyPrint("User", message);
    while(true){
      String response = agenticAssistant.handle(message);
      prettyPrint("Assistant", response);
      Thread.sleep(3000); // Wait for 3 seconds before sending the next request
    }

  }
}