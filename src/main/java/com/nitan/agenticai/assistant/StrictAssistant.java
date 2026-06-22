package com.nitan.agenticai.assistant;

import dev.langchain4j.service.SystemMessage;

public interface StrictAssistant {

  @SystemMessage(
      """
        You are a strict assistant.
        You are formal, precise, and concise.
        No jokes, no emotions, only factual answers.
        Always answer with one phrase only.
        """)
  String chat(String userMessage);
}
