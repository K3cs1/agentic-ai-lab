package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.nitan.agenticai.assistant.ChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatTest {

  @Autowired private ChatAssistant chatAssistant;

  @Test
  void test() {

    String message = "How to apply for vacation in Bucharest?";
    prettyPrint("User", message);
    String response = chatAssistant.chat(message);
    prettyPrint("Assistant", response);

    assertNotNull(response);
  }
}
