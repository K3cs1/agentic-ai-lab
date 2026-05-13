package com.nitan.agenticai.config;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.TokenCountEstimator;

public class CustomTokenEstimator implements TokenCountEstimator {
  @Override
  public int estimateTokenCountInText(String s) {
    return s.length() / 4;
  }

  @Override
  public int estimateTokenCountInMessage(ChatMessage message) {
    if (message == null) {
      return 0;
    }

    if (message instanceof UserMessage userMessage) {
      return estimateTokenCountInText(userMessage.singleText());
    }

    if (message instanceof AiMessage aiMessage) {
      return estimateTokenCountInText(aiMessage.text());
    }

    if (message instanceof SystemMessage systemMessage) {
      return estimateTokenCountInText(systemMessage.text());
    }

    // fallback for unknown message types
    return estimateTokenCountInText(message.toString());
  }

  @Override
  public int estimateTokenCountInMessages(Iterable<ChatMessage> iterable) {
    if (iterable == null) {
      return 0;
    }

    int total = 0;
    for (ChatMessage message : iterable) {
      total += estimateTokenCountInMessage(message);
    }
    return total;
  }
}
