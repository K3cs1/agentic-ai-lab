package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.nitan.agenticai.assistant.ChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatTest {

  @Autowired
  ChatAssistant qwenChatAssistant;

  @Autowired
  ChatAssistant llamaChatAssistant;

  @Test
  void test() {
    String message = "What is agentic ai?";

    prettyPrint("User", message);
    String response1 = qwenChatAssistant.chat(message);
    prettyPrint("Answer from model 1", response1);

    prettyPrint("User", message);
    String response2 = llamaChatAssistant.chat(message);
    prettyPrint("Answer from model 2", response2);

    assertNotEquals(response1, response2);// ⚠️
  }
}
