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
  void search() {

    String message = "How many vacation days do employees get?";
    prettyPrint("User", message);

    String answer = chatAssistant.answer(message);

    prettyPrint("Assistant", answer);

    assertNotNull(answer);
  }
}
