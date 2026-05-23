package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.AgenticAssistant;
import com.nitan.agenticai.service.PromptService;
import com.nitan.agenticai.tools.CurrencyTools;
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
        .temperature(0.8)
        .build();
  }

  @Bean
  AgenticAssistant chatAssistant(ChatModel chatModel, PromptService promptService, CurrencyTools currencyTools) {
    return AiServices.builder(AgenticAssistant.class)
        .chatModel(chatModel)
        .systemMessageProvider(context -> {
          String s = promptService.getSystemPrompt("my-agentic-assistant");
          System.out.println("System prompt: " + s);
          return s;
          }
        )
        .tools(currencyTools)
        .build();
  }
}
