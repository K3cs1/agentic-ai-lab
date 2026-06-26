package com.nitan.agenticai.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nitan.agenticai.assistant.FunnyAssistant;
import com.nitan.agenticai.assistant.ResponseStyleClassifierAssistant;
import com.nitan.agenticai.assistant.StrictAssistant;
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
        .build();
  }

  @Bean
  FunnyAssistant jokingAssistant(ChatModel chatModel) {
    return AiServices.builder(FunnyAssistant.class).chatModel(chatModel).build();
  }

  @Bean
  StrictAssistant strictAssistant(ChatModel chatModel) {
    return AiServices.builder(StrictAssistant.class).chatModel(chatModel).build();
  }

  @Bean
  ResponseStyleClassifierAssistant sentimentClassifierAssistant(
      ChatModel chatModel, ObjectMapper objectMapper) {
    return new ResponseStyleClassifierAssistant(chatModel, objectMapper);
  }
}
