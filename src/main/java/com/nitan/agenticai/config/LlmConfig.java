package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.ChatAssistant;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.TokenCountEstimator;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LlmConfig {

  private static final String BASE_OLLAMA_URL = "http://localhost:11434";
  private static final String MODEL = "qwen2.5";

  @Bean
  ChatModel chatLanguageModel() {
    return OllamaChatModel.builder()
        .baseUrl(BASE_OLLAMA_URL)
        .temperature(0.2)
        .modelName(MODEL)
        .numCtx(2048) // ⭐
        .numPredict(512) // ⭐
        .build();
  }

  @Bean // ⭐
  TokenCountEstimator estimator() {
    return new CustomTokenEstimator();
  }

  @Bean
  ChatAssistant chatAssistant(ChatModel chatModel) {
    return AiServices.builder(ChatAssistant.class)
        .chatModel(chatModel)
        .chatMemoryProvider(chatMemoryProvider())
        .build();
  }

  @Bean
  ChatMemoryProvider chatMemoryProvider() {
    return memoryId ->
        TokenWindowChatMemory.builder() // ⭐
            .id(memoryId)
            .maxTokens(1000, estimator()) // ⚠️
            .build();
  }
}
