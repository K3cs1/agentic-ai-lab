package com.nitan.agenticai.config;

import com.nitan.agenticai.dto.OllamaResponse;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class OllamaClient {

  private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:11434").build();

  public Flux<String> chatStream(String message) {

    Map<String, Object> request =
        Map.of(
            "model",
            "qwen2.5",
            "stream",
            true, // ⭐ enable streaming
            "messages",
            List.of(Map.of("role", "user", "content", message)));

    return webClient
        .post()
        .uri("/api/chat")
        .bodyValue(request)
        .retrieve()
        .bodyToFlux(OllamaResponse.class) // ⭐ expect a stream of responses this time
        .map(resp -> resp.message().content())
        .filter(content -> content != null && !content.isEmpty());
  }
}
