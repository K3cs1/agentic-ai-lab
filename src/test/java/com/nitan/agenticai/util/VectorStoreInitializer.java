package com.nitan.agenticai.util;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VectorStoreInitializer {

  @Autowired private EmbeddingModel embeddingModel;

  @Autowired private EmbeddingStore embeddingStore;

  @BeforeAll
  static void create_collection_if_not_exists() {
    QdrantClient client =
        new QdrantClient(QdrantGrpcClient.newBuilder("localhost", 6334, false).build());
    String collectionName = "company-kb";

    try {
      boolean exists = client.collectionExistsAsync(collectionName).get();

      if (exists) {
        System.out.println("Collection '" + collectionName + "' exists. Deleting...");
        client.deleteCollectionAsync(collectionName).get();
      }

      System.out.println("Creating collection '" + collectionName + "'...");
      client
          .createCollectionAsync(
              collectionName,
              Collections.VectorParams.newBuilder()
                  .setSize(2560) // Ensure this matches your embedding model
                  .setDistance(Collections.Distance.Cosine)
                  .build())
          .get();

      System.out.println("Setup completed successfully.");
      client.close();
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong during setup:", e);
    }
  }

  @Test
  void populate_collection() {
    // populate the vector store
    Document doc1 =
        FileSystemDocumentLoader.loadDocument("src/main/resources/docs/HR-corporate card.txt");
    doc1.metadata().put("department", "HR");
    Document doc2 =
        FileSystemDocumentLoader.loadDocument("src/main/resources/docs/HR-referals.txt");
    doc2.metadata().put("department", "HR");
    Document doc3 =
        FileSystemDocumentLoader.loadDocument("src/main/resources/docs/HR-vacation.txt");
    doc3.metadata().put("department", "HR");
    Document doc4 =
        FileSystemDocumentLoader.loadDocument("src/main/resources/docs/SUPPORT-contacts.txt");
    doc4.metadata().put("department", "SUPPORT");

    List<Document> documents = List.of(doc1, doc2, doc3, doc4);

    DocumentSplitter splitter =
        DocumentSplitters.recursive(
            300, // ⭐ max characters per chunk
            50 // ⭐ overlap between chunks
            );

    EmbeddingStoreIngestor ingestor =
        EmbeddingStoreIngestor.builder()
            .embeddingModel(embeddingModel)
            .embeddingStore(embeddingStore)
            .documentSplitter(splitter) // ⭐
            .build();

    ingestor.ingest(documents);
  }
}
