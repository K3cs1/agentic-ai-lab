package com.nitan.agenticai.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ChatAssistant {
  @SystemMessage("""
    You are a helpful assistant for Accenture employees.
    Answer only using the provided context.
    If the answer cannot be found in the context, say:
    "I don't know, based on the provided context."
    Do not invent information.
    """)

  @UserMessage("""
      Context:
      {{context}}

      Question:
      {{question}}
      """)
  String chat(@V("context") String context, @V("question") String question);
}
