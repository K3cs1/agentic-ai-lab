package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;

import com.nitan.agenticai.config.OllamaClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatTest {

  @Autowired private OllamaClient ollamaClient;

  @Test
  void test() {
    chat("Hello Ollama , my name is Nicusor!");
  }

  private void chat(String message) {
    prettyPrint("User", message);
    String response = ollamaClient.chat(message);// ⚠️
    prettyPrint("Ollama", response);
  }
}
