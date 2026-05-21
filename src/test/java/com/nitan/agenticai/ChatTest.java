package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;
import static com.nitan.agenticai.util.Util.prettyPrintLabel;

import com.nitan.agenticai.config.OllamaClient;
import com.nitan.agenticai.util.Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatTest {

  @Autowired private OllamaClient ollamaClient;

  @Test
  void test() {
    chat("Explain hexagonal architecture?");
  }

  private void chat(String message) {
    prettyPrint("User", message);
    prettyPrintLabel("Ollama says:");
    ollamaClient
        .chatStream(message)
        .doOnNext(Util::prettyPrintMessage) // streaming output
        .blockLast(); // wait until completion

    System.out.println("\n");
  }
}
