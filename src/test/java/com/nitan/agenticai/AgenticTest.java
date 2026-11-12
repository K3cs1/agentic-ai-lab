package com.nitan.agenticai;

import com.nitan.agenticai.assistant.Assistant;
import dev.langchain4j.mcp.client.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
 class AgenticTest {

  @Autowired
  private Assistant assistant;

  @Autowired
  private McpClient mcpClient;

 @Test
 void reviewFile() {
  McpGetPromptResult promptResult = mcpClient.getPrompt(
          "review-file",
          Map.of("path", "/src/Calculator.java"));

  StringBuilder userText = new StringBuilder();
  for (McpPromptMessage pm : promptResult.messages()) {
   McpPromptContent content = pm.content();
   if (content instanceof McpTextContent tc) {
    userText.append(tc.text()).append("\n\n");
   } else if (content instanceof McpEmbeddedResource er
           && er.resource() instanceof McpTextResourceContents trc) {
    userText.append(trc.text());
   }
  }

  String answer = assistant.answer(userText.toString());

  System.out.println("Answer:\n" + answer);
 }
 }