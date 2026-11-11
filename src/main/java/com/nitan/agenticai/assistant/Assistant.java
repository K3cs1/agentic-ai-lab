package com.nitan.agenticai.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface Assistant {

  @SystemMessage(
      """
        You are a helpful assistant.
    """)
  String answer(@UserMessage String question);
}
