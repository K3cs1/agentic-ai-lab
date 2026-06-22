package com.nitan.agenticai.assistant;

import dev.langchain4j.service.SystemMessage;

public interface ResponseStyleClassifierAssistant {

  @SystemMessage(
      """
        Classify the tone of the input text.
        Allowed labels:
        - FUNNY
        - STRICT
    """)
  String classify(String input);
}
