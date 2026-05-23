package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.AgenticAssistant;
import com.nitan.agenticai.tools.BilingTools;
import com.nitan.agenticai.tools.CrmTools;
import com.nitan.agenticai.tools.PricingTools;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LlmConfig {

  private static final String BASE_OLLAMA_URL = "http://localhost:11434";
  private static final String MODEL = "qwen3:8b";

  @Bean
  ChatModel chatLanguageModel() {
    return OllamaChatModel.builder()
        .baseUrl(BASE_OLLAMA_URL)
        .modelName(MODEL)
        .think(true) //⭐
        .returnThinking(true) //⭐
        .temperature(0.0)
        .listeners(List.of(new ThinkingLogger())) //⭐
        .build();
  }

  @Bean
  AgenticAssistant agenticAssistant(
      ChatModel chatModel, CrmTools crmTools, BilingTools bilingTools, PricingTools pricingTools) {
    return AiServices.builder(AgenticAssistant.class)
        .chatModel(chatModel)
        .tools(crmTools, bilingTools, pricingTools)
        .build();
  }
}
