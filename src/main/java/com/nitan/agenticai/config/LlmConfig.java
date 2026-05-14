package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.ChatAssistant;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class LlmConfig {

  private static final String BASE_OLLAMA_URL = "http://localhost:11434";
  private static final String MODEL = "qwen2.5";

  @Bean
  ChatModel chatLanguageModel() {
    return OllamaChatModel.builder()
        .baseUrl(BASE_OLLAMA_URL)
        .modelName(MODEL)
        .numCtx(2048)
        .numPredict(512)
        .build();
  }

  @Bean
  ChatAssistant statefulChatAssistant(ChatModel chatModel, JdbcTemplate jdbcTemplate) {
    return AiServices.builder(ChatAssistant.class)
        .chatModel(chatModel)
        .chatMemoryProvider(chatMemoryProvider(jdbcTemplate))
        .build();
  }


  @Bean
  ChatMemoryStore chatMemoryStore(JdbcTemplate jdbcTemplate) {
    return new PostgresChatMemoryStore(jdbcTemplate); //⭐
  }

  @Bean
  ChatMemoryProvider chatMemoryProvider(JdbcTemplate jdbcTemplate) {
    return memoryId ->
        MessageWindowChatMemory.builder()
            .id(memoryId)
            .maxMessages(100)
            .chatMemoryStore(chatMemoryStore(jdbcTemplate))
            .build();
  }
}