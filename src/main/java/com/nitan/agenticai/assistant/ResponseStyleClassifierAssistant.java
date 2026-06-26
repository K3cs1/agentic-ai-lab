package com.nitan.agenticai.assistant;

import com.nitan.agenticai.domain.ResponseStyle;
import dev.langchain4j.service.SystemMessage;



public interface ResponseStyleClassifierAssistant {

  /**
   * When the ⭐
   */
  @SystemMessage(
      """
        Classify the tone of the input text.

        Allowed labels:
        - FUNNY
        - STRICT
    """)
  /**
   * LangChain4j automatically enables native structured output for non-scalar return types .This
   * includes enums, records, POJOs, and collections.
   * The framework generates an output schema and requests the model to return data conforming to
   * that schema when the underlying model provider supports structured output.
   */
  ResponseStyle classify(String input);
}
