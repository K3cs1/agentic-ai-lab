package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.filter.Filter;
import dev.langchain4j.store.embedding.filter.comparison.IsEqualTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SimilaritySearchTest {

  @Autowired private EmbeddingModel embeddingModel;

  @Autowired private EmbeddingStore embeddingStore;

  @Test
  void search() {
    Filter myFilter = new IsEqualTo("department", "HR"); // ⭐

    Embedding queryEmbedding =
        embeddingModel.embed("How many vacation days do employees have?").content();

    EmbeddingSearchRequest embeddingSearchRequest =
        EmbeddingSearchRequest.builder()
            .queryEmbedding(queryEmbedding)
            .maxResults(5) // ⚠️
            .filter(myFilter) // ⭐
            .build();

    EmbeddingSearchResult matches = embeddingStore.search(embeddingSearchRequest);

    matches.matches().forEach(match -> prettyPrint("MATCH",match.toString()));
    assertTrue(matches.matches().size() > 0);
  }
}
