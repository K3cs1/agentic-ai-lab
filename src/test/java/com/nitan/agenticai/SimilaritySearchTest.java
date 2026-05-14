package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SimilaritySearchTest {

  @Autowired private EmbeddingModel embeddingModel;

  @Autowired private EmbeddingStore store;

  @BeforeEach
  void populateVectorStore() {
    System.out.println("Populating the vector store with documents...");
    Document doc1 =
        FileSystemDocumentLoader.loadDocument("src/main/resources/docs/HR-corporate card.txt");
    Document doc2 =
        FileSystemDocumentLoader.loadDocument("src/main/resources/docs/HR-referals.txt");
    Document doc3 =
        FileSystemDocumentLoader.loadDocument("src/main/resources/docs/HR-vacation.txt");

    List<Document> docs = List.of(doc1, doc2, doc3);

    docs.forEach(doc -> {
      Embedding embedding = embeddingModel.embed(doc.text()).content();
      store.add(embedding, TextSegment.from(doc.text()));
    });
    System.out.println("Documents ingested successfully!");
  }

  @Test
  void shouldRetrieveMostRelevantDocument() {
    Embedding query = embeddingModel.embed("How many vacation days can I get?").content();

    EmbeddingSearchResult<TextSegment> result = store.search(
        EmbeddingSearchRequest.builder()
            .queryEmbedding(query)
            .maxResults(1)
            .build()
    );

    result.matches().forEach(match -> {
      prettyPrint("MATCH", match.toString());
    });
  }
}