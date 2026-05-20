package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.ChatAssistant;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LlmConfig {

  private static final String BASE_OLLAMA_URL = "http://localhost:11434";
  private static final String CHAT_MODEL = "qwen2.5";
  private static final String EMBEDDING_MODEL = "qwen3-embedding:4b";

  @Bean
  ChatModel chatLanguageModel() {
    return OllamaChatModel.builder()
        .baseUrl(BASE_OLLAMA_URL)
        .modelName(CHAT_MODEL)
        .temperature(0.0)
        .build();
  }

  @Bean
  ChatAssistant chatAssistant(ChatModel chatModel) {
    ContentRetriever retriever =
        EmbeddingStoreContentRetriever.builder()
            .embeddingModel(embeddingModel())
            .embeddingStore(embeddingStore())
            .maxResults(5)
            .minScore(0.7)
            .build();
    return AiServices.builder(ChatAssistant.class)
        .chatModel(chatModel)
        .contentRetriever(retriever)
        .build();
  }

  @Bean
  EmbeddingModel embeddingModel() {
    return OllamaEmbeddingModel.builder()
        .baseUrl(BASE_OLLAMA_URL)
        .modelName(EMBEDDING_MODEL)
        .build();
  }

  @Bean
  EmbeddingStore<TextSegment> embeddingStore() {
    return QdrantEmbeddingStore.builder()
        .host("localhost")
        .port(6334)
        .collectionName("company-kb")
        .build();
  }
}
