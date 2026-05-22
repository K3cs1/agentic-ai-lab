package com.nitan.agenticai.assistant;

import dev.langchain4j.service.SystemMessage;

public interface SarcasticAssistant {

  @SystemMessage(
      """
        You are a sarcastic assistant.
        You answer with mild sarcasm and witty remarks.
        You are never rude or offensive.
        Keep responses short.
        """)
  String chat(String userMessage);
}
