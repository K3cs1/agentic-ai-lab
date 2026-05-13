package com.nitan.agenticai.config;

import com.nitan.agenticai.dto.OllamaResponse;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OllamaClient {

  private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:11434").build();

  public String chat(String message) {

    Map<String, Object> request =
        Map.of(
            "model",
            "qwen2.5",
            "stream",
            false, // ⚠️
            "messages",
            List.of(Map.of("role", "user", "content", message)));

    return webClient
        .post()
        .uri("/api/chat")
        .bodyValue(request)
        .retrieve()
        .bodyToMono(OllamaResponse.class) // ⚠️
        .map(r -> r.message().content()) // ⚠️
        .block();
  }
}
