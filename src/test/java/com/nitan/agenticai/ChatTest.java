package com.nitan.agenticai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nitan.agenticai.assistant.FunnyAssistant;
import com.nitan.agenticai.assistant.ResponseStyleClassifierAssistant;
import com.nitan.agenticai.assistant.StrictAssistant;
import com.nitan.agenticai.domain.ResponseStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatTest {
  @Autowired private FunnyAssistant funnyAssistant;

  @Autowired private StrictAssistant strictAssistant;

  @Autowired private ResponseStyleClassifierAssistant responseStyleClassifierAssistant;

  private ObjectMapper objectMapper = new ObjectMapper();

  private String question = "What is the best way to learn java?";

  @Test // ⭐
  void should_return_funny() throws Exception {
    String response =
        responseStyleClassifierAssistant.classify(
            funnyAssistant.chat(question));
    assertNotNull(response);
    ResponseStyle responseStyle = objectMapper.readValue(response, ResponseStyle.class);
    assertEquals("FUNNY", responseStyle.style().name());
  }

  @Test // ⭐
  void should_return_strict() throws Exception {
    String response =
        responseStyleClassifierAssistant.classify(
            strictAssistant.chat(question));
    assertNotNull(response);
    ResponseStyle responseStyle = objectMapper.readValue(response, ResponseStyle.class);
    assertEquals("STRICT", responseStyle.style().name());
  }
}
