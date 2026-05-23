package com.nitan.agenticai.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;

public interface AgenticAssistant {
  @SystemMessage(
      """
          You are an AI assistant.
      """)
  TokenStream handle(String message);
}