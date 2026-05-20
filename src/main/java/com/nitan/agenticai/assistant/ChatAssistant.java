package com.nitan.agenticai.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface ChatAssistant {

  @SystemMessage(
      """
        You are a helpful assistant.
        Answer only based on provided context.
        If context is insufficient, say you don't know.
    """)
  String answer(@UserMessage String question);
}
