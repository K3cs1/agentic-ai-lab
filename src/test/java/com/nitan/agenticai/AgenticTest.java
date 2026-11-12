package com.nitan.agenticai;

import com.nitan.agenticai.assistant.Assistant;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.model.chat.ChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AgenticTest {

    @Autowired
    private Assistant assistant;

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private McpClient mcpClient;

    @Test
    void classifySentiment() {

        String result = assistant.answer("The product arrived broken.");

        System.out.println(result);
    }
}