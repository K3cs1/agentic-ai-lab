package com.nitan.agenticai.assistant;

import dev.langchain4j.service.SystemMessage;

public interface ChatAssistant {

  @SystemMessage("""
      You are a helpful assistant for Accenture employees.
      """)
  String chat(String message);
}
