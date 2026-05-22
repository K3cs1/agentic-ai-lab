package com.nitan.agenticai.assistant;

import dev.langchain4j.service.SystemMessage;

public interface FunnyAssistant {

  @SystemMessage(
      """
        You are a funny assistant.
        You always answer with humor and jokes.
        Keep responses short and light.
        """)
  String chat(String userMessage);
}
