package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.AgenticAssistant;
import com.nitan.agenticai.tools.CurrencyTools;
import com.nitan.agenticai.tools.WeatherTools;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.search.ToolSearchStrategy;
import dev.langchain4j.service.tool.search.vector.VectorToolSearchStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LlmConfig {

  private static final String BASE_OLLAMA_URL = "http://localhost:11434";
  private static final String EMBEDDING_MODEL = "qwen3-embedding:4b";
  private static final String MODEL = "qwen2.5";

  @Bean
  ChatModel chatModel() {
    return OllamaChatModel.builder()
        .baseUrl(BASE_OLLAMA_URL)
        .modelName(MODEL)
        .logRequests(true)
        .logResponses(true)
        .temperature(0.2)
        .build();
  }

  @Bean // ⭐
  EmbeddingModel embeddingModel() {
    return OllamaEmbeddingModel.builder()
        .baseUrl(BASE_OLLAMA_URL)
        .modelName(EMBEDDING_MODEL).build();
  }

  @Bean // ⭐
  ToolSearchStrategy toolSearchStrategy() {
    return VectorToolSearchStrategy.builder()
        .embeddingModel(embeddingModel())
        .maxResults(1)
        .build();
  }

  @Bean
  AgenticAssistant agenticAssistant(ChatModel chatModel, CurrencyTools currencyTools,
      WeatherTools weatherTools) {
    return AiServices.builder(AgenticAssistant.class)
        .chatModel(chatModel)
        .tools(currencyTools, weatherTools)
        .toolSearchStrategy(toolSearchStrategy())
        .build();
  }
}
