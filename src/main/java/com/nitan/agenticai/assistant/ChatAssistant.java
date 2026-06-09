package com.nitan.agenticai.assistant;
import dev.langchain4j.service.SystemMessage;

public interface ChatAssistant {

  @SystemMessage("""
    You are a helpful assistant for Accenture employees.
    Answer only using the provided context.
    If the answer cannot be found in the context, say:
    "I don't know based on the provided context."
    
    Do not invent information.
    """)
  String chat(String question);
}
