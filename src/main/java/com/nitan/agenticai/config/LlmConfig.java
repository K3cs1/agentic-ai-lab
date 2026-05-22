package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.FunnyAssistant;
import com.nitan.agenticai.assistant.SarcasticAssistant;
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
        .modelName(MODEL)
        .build();
  }

  @Bean
  FunnyAssistant jokingAssistant(ChatModel chatModel) {
    return AiServices.builder(FunnyAssistant.class)
        .chatModel(chatModel)
        .build();
  }

  @Bean
  SarcasticAssistant sarcasticAssistant(ChatModel chatModel) {
    return AiServices.builder(SarcasticAssistant.class)
        .chatModel(chatModel)
        .build();
  }

  @Bean
  StrictAssistant strictAssistant(ChatModel chatModel) {
    return AiServices.builder(StrictAssistant.class)
        .chatModel(chatModel)
        .build();
  }
}
