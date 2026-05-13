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

  private final ChatMemory memory = MessageWindowChatMemory.withMaxMessages(10);//⭐

  @Test
  void test() {

    String message = "My name is Nicusor";
    memory.add(UserMessage.from(message));//⚠️
    String response = statefulChatAssistant.chat(memory.messages());
    memory.add(AiMessage.from(response));//⚠️
    prettyPrint("User", message);
    prettyPrint("Assistant", response);

    message = "What is the name of the USA first president?";
    memory.add(UserMessage.from(message));//⚠️
    response = statefulChatAssistant.chat(memory.messages());
    memory.add(AiMessage.from(response));//⚠️
    prettyPrint("User", message);
    prettyPrint("Assistant", response);

    message = "What is my name?";
    memory.add(UserMessage.from(message));//⚠️
    response = statefulChatAssistant.chat(memory.messages());
    memory.add(AiMessage.from(response));//⚠️
    prettyPrint("User", message);
    prettyPrint("Assistant", response);
  }
}