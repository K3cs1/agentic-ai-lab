package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;

import com.nitan.agenticai.assistant.FunnyAssistant;
import com.nitan.agenticai.assistant.SarcasticAssistant;
import com.nitan.agenticai.assistant.StrictAssistant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatTest {
  @Autowired FunnyAssistant funnyAssistant;
  @Autowired SarcasticAssistant sarcasticAssistant;
  @Autowired StrictAssistant strictAssistant;

  private String message = "Tell me a joke about AI";

  @BeforeEach
  void setUp() {
    prettyPrint("User", message);
  }

  @Test
  void testFunnyAssistant() {
    prettyPrint("Funny Assistant", funnyAssistant.chat(message));
  }

  @Test
  void testSarcasticAssistant() {
    prettyPrint("Sarcastic Assistant", sarcasticAssistant.chat(message));
  }

  @Test
  void testStrictAssistant() {
    prettyPrint("Strict Assistat", strictAssistant.chat(message));
  }
}
