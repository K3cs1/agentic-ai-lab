package com.nitan.agenticai;

import com.nitan.agenticai.assistant.Assistant;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.McpResourceContents;
import dev.langchain4j.mcp.client.McpTextResourceContents;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AgenticTest {

    @Autowired
    private Assistant assistant;
    @Autowired
    private McpClient mcpClient;

    @Test
    void vacationQuestion() {
        String policy = readText("policy://vacation");

        String prompt = """
                Answer using only the policy below.
                
                POLICY:
                %s
                
                QUESTION:
                How many vacation days do I have, and how long can I carry them over?
                """.formatted(policy);

        System.out.println(assistant.answer(prompt));
    }

    @Test
    void ticketQuestion() {
        String ticket = readText("ticket://TCK-1");

        String prompt = """
                Summarize this ticket in one sentence and suggest a next step.
                
                TICKET TCK-1:
                %s
                """.formatted(ticket);

        System.out.println(assistant.answer(prompt));
    }

    private String readText(String uri) {
        McpResourceContents contents = mcpClient.readResource(uri).contents().get(0);
        return ((McpTextResourceContents) contents).text();
    }
}