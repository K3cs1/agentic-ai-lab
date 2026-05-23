package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;

import com.nitan.agenticai.assistant.AgenticAssistant;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AgenticTest {

  @Autowired private AgenticAssistant agenticAssistant;

  @Test
  void test() {
    String message = "What is the final invoice amount for Acme Corporation?";
    prettyPrint("User", message);
    AtomicBoolean isDone = new AtomicBoolean(false);
    agenticAssistant
        .handle(message)
        .onPartialThinking(token -> System.out.print(token.text())) // ⭐ thinking tokens
        .onPartialResponse(token -> System.out.print(token))
        .onCompleteResponse(
            response -> {
              prettyPrint("\nAssistant", response.aiMessage().text());
              isDone.set(true); // mark the response as done
            })
        .onError(Throwable::printStackTrace)
        .start();
    while (!isDone.get()) {
      try {
        Thread.sleep(2000); // wait for the response to complete
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
