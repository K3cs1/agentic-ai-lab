package com.nitan.agenticai;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.mcp.client.*;
import dev.langchain4j.model.chat.ChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AgenticTest {

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private McpClient mcpClient;

    @Test
    void classifySentiment() {
        McpGetPromptResult promptResult = mcpClient.getPrompt(
                "classify-sentiment",
                Map.of("text", "The battery died after one day"));

        List<ChatMessage> messages = new ArrayList<>();
        for (McpPromptMessage pm : promptResult.messages()) {
            String text = ((McpTextContent) pm.content()).text();
            if (pm.role() == McpRole.USER) {
                messages.add(UserMessage.from(text));
            } else {
                messages.add(AiMessage.from(text));
            }
        }

        String answer = chatModel.chat(messages).aiMessage().text();

        messages.forEach(System.out::println);
        System.out.println("----");
        System.out.println(answer);

        assertThat(answer.trim()).isEqualToIgnoringCase("NEGATIVE");
    }
}