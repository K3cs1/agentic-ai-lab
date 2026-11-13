package com.nitan.agenticai;

import com.nitan.agenticai.assistant.Assistant;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.McpGetPromptResult;
import dev.langchain4j.mcp.client.McpTextContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
 class AgenticTest {

  @Autowired
  private Assistant assistant;

  @Autowired
  private McpClient mcpClient;

 @Test
 void reviewCode() {
  McpGetPromptResult promptResult = mcpClient.getPrompt(
          "review-code",
          Map.of("language", "Java", "code", """
                  int x = null;
                  """));

  String userText = ((McpTextContent) promptResult.messages().get(0).content()).text();

  String answer = assistant.answer(userText);

  System.out.println(answer);

  assertThat(answer).isNotBlank();
 }
 }