package com.nitan.agenticai.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nitan.agenticai.dto.Prompt;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

@Repository
@RequiredArgsConstructor
public class PromptRepository {

  @Value("${langfuse.authorizationHeader}")
  private String authorizationHeader;

  private RestClient restClient;

  @PostConstruct
  private void init() {
    restClient = RestClient.builder()
        .baseUrl("http://localhost:3000")
        .defaultHeader("Authorization", "Basic " + authorizationHeader)
        .build();
  }

  public Optional<Prompt> findByKey(String promptKey) {
    try {
      String json =
          restClient
              .get()
              .uri("/api/public/v2/prompts/{name}?label=production", promptKey)
              .retrieve()
              .body(String.class);

      // parse name, version, prompt text from JSON
      JsonNode node = new ObjectMapper().readTree(json);
      return Optional.of(
          new Prompt(
              null,
              node.get("name").asText(),
              node.get("version").asInt(),
              node.get("prompt").asText()));
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }
}
