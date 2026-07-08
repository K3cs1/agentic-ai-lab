package com.nitan.agenticai.util;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
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

  @BeforeAll // ⭐
  static void createCollectionIfNotExists() {
    QdrantClient client =
        new QdrantClient(QdrantGrpcClient.newBuilder("localhost", 6334, false).build());
    String collectionName = "company-kb";

    try {
      // 1. Check if the collection exists
      boolean exists = client.collectionExistsAsync(collectionName).get();

      // 2. If it exists, delete it first
      if (exists) {
        System.out.println("Collection '" + collectionName + "' exists. Deleting...");
        client.deleteCollectionAsync(collectionName).get();
      }

      // 3. Create the collection
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
  void populateCollection() {
    System.out.println("Populating collection with documents...");
    Document doc1 =
        FileSystemDocumentLoader.loadDocument("src/main/resources/docs/HR-corporate card.txt");
    Document doc2 =
        FileSystemDocumentLoader.loadDocument("src/main/resources/docs/HR-referals.txt");
    Document doc3 =
        FileSystemDocumentLoader.loadDocument("src/main/resources/docs/HR-vacation.txt");

    List<Document> documents = List.of(doc1, doc2, doc3);

    EmbeddingStoreIngestor ingestor =
        EmbeddingStoreIngestor.builder()
            .embeddingModel(embeddingModel)
            .embeddingStore(embeddingStore)
            .build();

    ingestor.ingest(documents);
    System.out.println("Documents ingested successfully.");
  }
}
