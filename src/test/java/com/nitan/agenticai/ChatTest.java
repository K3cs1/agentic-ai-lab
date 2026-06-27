package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;
import com.nitan.agenticai.assistant.ChatAssistant;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatTest {
  @Autowired private ChatAssistant statefulChatAssistant;

  @Test
  void test() {

    String message = "My name is Nicusor";
    String response = statefulChatAssistant.chat(message);
    prettyPrint("User", message);
    prettyPrint("Assistant", response);

    message = "What is the name of the USA first president?";
    response = statefulChatAssistant.chat(message);
    prettyPrint("User", message);
    prettyPrint("Assistant", response);

    message = "What is my name?";
    response = statefulChatAssistant.chat(message);
    prettyPrint("User", message);
    prettyPrint("Assistant", response);
  }
}