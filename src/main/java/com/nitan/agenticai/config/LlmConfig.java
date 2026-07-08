package com.nitan.agenticai.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LlmConfig {

  private static final String BASE_OLLAMA_URL = "http://localhost:11434";
  private static final String MODEL = "qwen3-embedding:4b";

  @Bean
  EmbeddingModel embeddingModel() {
    return OllamaEmbeddingModel.builder()
        .baseUrl(BASE_OLLAMA_URL)
        .modelName(MODEL)
        .build();
  }

  @Bean
  EmbeddingStore<TextSegment> embeddingStore() {
    return QdrantEmbeddingStore.builder() // ⭐
        .host("localhost")
        .port(6334) // ⚠️ just for evidence as the default port is 6334 anyway.
        .collectionName("company-kb")
        .build();
  }
}
