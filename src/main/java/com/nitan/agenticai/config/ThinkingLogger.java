package com.nitan.agenticai.config;

import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;

public class ThinkingLogger implements ChatModelListener {

  @Override
  public void onResponse(ChatModelResponseContext context) {
    String thinking = context.chatResponse().aiMessage().thinking();
    if (thinking != null) {
      System.out.println("=== THINKING ===\n" + thinking);
    }
  }
}
