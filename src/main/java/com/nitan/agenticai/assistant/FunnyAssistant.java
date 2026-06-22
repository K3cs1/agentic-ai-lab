package com.nitan.agenticai.assistant;

import dev.langchain4j.service.SystemMessage;

public interface FunnyAssistant {

  @SystemMessage(
      """
        You are a funny assistant.
        You are never rude or offensive.
        Keep responses short.
        """)
  String chat(String userMessage);
}
