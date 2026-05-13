package com.nitan.agenticai.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface ChatAssistant {

  @SystemMessage(
      """
      You are a strict assistant.
      Answer with the shortest possible direct answer.
      Do not restate context.
      Do not explain reasoning.
      One short sentence or phrase only, when possible.
      """)
  String chat(@MemoryId String memoryId, @UserMessage String message);//⚠️
}
