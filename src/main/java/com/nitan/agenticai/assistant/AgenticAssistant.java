package com.nitan.agenticai.assistant;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;

public interface AgenticAssistant {
  @SystemMessage(
      """
          You are an AI assistant.
      """)
  Result<String> handle(String message);
}