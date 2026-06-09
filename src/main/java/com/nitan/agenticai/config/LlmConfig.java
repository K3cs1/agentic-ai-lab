package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.ChatAssistant;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import java.util.List;
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
        .temperature(0.0)
        .build();
  }

  @Bean
  ContentRetriever retriever(){
    return query -> {
      System.out.println("Retrieving content for query: " + query.text());
      return List.of(
          Content.from("""
                Employees in Bucharest can apply for vacation using the HR Portal.
                Vacation requests require top mangmt approval.
             """));
    };
  }

  @Bean
  ChatAssistant chatAssistant(ChatModel chatModel) {
    return AiServices.builder(ChatAssistant.class)
        .chatModel(chatModel)
        .contentRetriever(retriever())
        .build();
  }
}
