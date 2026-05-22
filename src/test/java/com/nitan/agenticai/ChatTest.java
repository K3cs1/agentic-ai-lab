package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nitan.agenticai.assistant.ChatAssistant;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatTest {

  @Autowired
  private ChatAssistant statefulChatAssistant;

  private List<ChatMessage> messages = new ArrayList<>();

  @Test
  void test() {

    String message = "My name is Nicusor";
    pushMessage(UserMessage.from(message));
    String response = statefulChatAssistant.chat(messages);
    pushMessage(AiMessage.from(response));
    prettyPrint("User", message);
    prettyPrint("Assistant", response);

    message = "What is the name of the capital of USA?";
    pushMessage(UserMessage.from(message));
    response = statefulChatAssistant.chat(messages);
    pushMessage(AiMessage.from(response));
    prettyPrint("Assistant", response);
    prettyPrint("User", message);

    message = "What is my name?";
    pushMessage(UserMessage.from(message));
    response = statefulChatAssistant.chat(messages);
    pushMessage(AiMessage.from(response));
    prettyPrint("User", message);
    prettyPrint("Assistant", response);
    assertTrue(response.contains("Nicusor"));
  }

  private void pushMessage(ChatMessage message) {
    messages.add(message);
  }
}
