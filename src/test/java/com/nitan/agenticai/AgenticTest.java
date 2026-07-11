package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.nitan.agenticai.assistant.AgenticAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AgenticTest {

  @Autowired private AgenticAssistant agenticAssistant;

  @Test
  void test() {
    String message = "What is the exchange rate from USD to EUR?";
    prettyPrint("User", message);

    String response = agenticAssistant.handle(message);
    prettyPrint("Assistant", response);
    assertNotNull(response);
  }
}
