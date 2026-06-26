package com.nitan.agenticai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.nitan.agenticai.assistant.FunnyAssistant;
import com.nitan.agenticai.assistant.ResponseStyleClassifierAssistant;
import com.nitan.agenticai.assistant.StrictAssistant;
import com.nitan.agenticai.domain.ResponseStyle;
import com.nitan.agenticai.domain.Style;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatTest {

  @Autowired private FunnyAssistant funnyAssistant;

  @Autowired private StrictAssistant strictAssistant;

  @Autowired private ResponseStyleClassifierAssistant responseStyleClassifierAssistant;

  private String question = "What is the best way to learn java?";

  @Test
  void should_return_funny() {
    ResponseStyle response = responseStyleClassifierAssistant.classify(funnyAssistant.chat(question));
    assertNotNull(response);
    assertEquals(Style.FUNNY, response.style());
  }

  @Test
  void should_return_strict() {
    ResponseStyle response = responseStyleClassifierAssistant.classify(strictAssistant.chat(question));
    assertNotNull(response);
    assertEquals(Style.STRICT, response.style());
  }
}
