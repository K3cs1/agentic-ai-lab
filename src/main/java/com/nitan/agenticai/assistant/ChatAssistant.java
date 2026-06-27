package com.nitan.agenticai.assistant;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.service.SystemMessage;
import java.util.List;

public interface ChatAssistant {
  @SystemMessage(
      """
          You are a strict assistant.
          Be polite and answer with the shortest possible direct answer.
        """)
  String chat(String messages);
}
