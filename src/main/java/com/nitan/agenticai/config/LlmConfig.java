package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.ChatAssistant;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LlmConfig {

  private static final String BASE_OLLAMA_URL = "http://localhost:11434";
  private static final String MODEL_QWEN = "qwen2.5";
  private static final String MODEL_LLAMA = "llama3.1:8b";


  @Bean
  ChatModel qwenModel() {
    return OllamaChatModel.builder()
        .baseUrl(BASE_OLLAMA_URL)
        .modelName(MODEL_QWEN)
        .build();
  }

  @Bean
  ChatModel llamaModel() {
    return OllamaChatModel.builder()
        .baseUrl(BASE_OLLAMA_URL)
        .modelName(MODEL_LLAMA)
        .build();
  }

  @Bean
  ChatAssistant qwenChatAssistant(ChatModel qwenModel) {
    return AiServices.builder(ChatAssistant.class)
        .chatModel(qwenModel)
        .build();
  }

  @Bean
  ChatAssistant llamaChatAssistant(ChatModel llamaModel) {
    return AiServices.builder(ChatAssistant.class)
        .chatModel(llamaModel)
        .build();
  }
}
