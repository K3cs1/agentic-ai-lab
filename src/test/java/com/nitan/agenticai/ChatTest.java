package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;
import com.nitan.agenticai.assistant.ChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatTest {

  @Autowired private ChatAssistant assistant;

  @Test
  void test() {
    String message = "My name is Nicusor";
    String response = assistant.chat("conversationId_1", message);
    prettyPrint("User", message);
    prettyPrint("Assistant", response);

    message = "What is the name of the capital of USA?";
    response = assistant.chat("conversationId_2", message);
    prettyPrint("User", message);
    prettyPrint("Assistant", response);

    message = "What is my name?";
    response = assistant.chat("conversationId_1", message);
    prettyPrint("User", message);
    prettyPrint("Assistant", response);
  }
}
