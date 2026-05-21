package com.nitan.agenticai.assistant;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import org.springframework.stereotype.Component;

@Component
public class MyAssistant {

  private ChatModel chatModel;

  public MyAssistant(ChatModel chatModel) {
    this.chatModel = chatModel;
  }

  public String chat(String message) {
    ChatRequest request = ChatRequest.builder()
        .messages(SystemMessage.from("""
        You are a funny assistant.
        You always answer with humor and jokes.
        Keep responses short and light.
        """),UserMessage.from(message))
        .build();

    ChatResponse response = chatModel.chat(request);

    return response.aiMessage().text();
  }
}
