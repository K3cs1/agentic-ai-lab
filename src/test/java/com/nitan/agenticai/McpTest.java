package com.nitan.agenticai;

import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.McpGetPromptResult;
import dev.langchain4j.mcp.client.McpPromptContent;
import dev.langchain4j.mcp.client.McpPromptMessage;
import dev.langchain4j.mcp.client.McpRole;
import dev.langchain4j.mcp.client.McpTextContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class McpTest {
    @Autowired
    private McpClient mcpClient;

    @Test
    void getsCodeReviewerPersonaPrompt() {
        McpGetPromptResult result = mcpClient.getPrompt(
                "code-reviewer-persona", Map.of("language", "Java"));

        assertThat(result.description()).isEqualTo("Reviewer persona");
        assertThat(result.messages()).hasSize(1);
        assertThat(result.messages().get(0).role()).isEqualTo(McpRole.USER);
        assertThat(text(result.messages().get(0).content()))
                .isEqualTo("You are a strict Java code reviewer. Be concise.");
    }

    @Test
    void getsReviewCodePrompt() {
        String code = """
                public int divide(int a, int b) {
                    return a / b;
                }
                """;

        McpGetPromptResult result = mcpClient.getPrompt(
                "review-code", Map.of("language", "Java", "code", code));

        assertThat(result.description()).isEqualTo("Code review request");
        assertThat(result.messages()).hasSize(1);
        assertThat(result.messages().get(0).role()).isEqualTo(McpRole.USER);
        assertThat(text(result.messages().get(0).content()))
                .isEqualTo("Review this Java code and list concrete problems:\n\n" + code);
    }

    @Test
    void getsReviewFilePromptWithEmbeddedResource() {
        McpGetPromptResult result = mcpClient.getPrompt(
                "review-file", Map.of("path", "/src/Calculator.java"));

        assertThat(result.description()).isEqualTo("Review file");
        assertThat(result.messages()).hasSize(2);
        assertThat(result.messages().get(0).role()).isEqualTo(McpRole.USER);
        assertThat(text(result.messages().get(0).content())).isEqualTo("Review this file:");

        McpPromptMessage embeddedResource = result.messages().get(1);
        assertThat(embeddedResource.role()).isEqualTo(McpRole.USER);
        assertThat(embeddedResource.content().type()).isEqualTo(McpPromptContent.Type.RESOURCE);
        System.out.println("Embedded resource: " + embeddedResource.content());
    }

    @Test
    void getsFewShotSentimentPrompt() {
        McpGetPromptResult result = mcpClient.getPrompt(
                "classify-sentiment", Map.of("text", "The support team was wonderful"));

        assertThat(result.description()).isEqualTo("Sentiment");
        assertThat(result.messages()).hasSize(5);
        assertThat(result.messages())
                .extracting(McpPromptMessage::role)
                .containsExactly(
                        McpRole.USER,
                        McpRole.ASSISTANT,
                        McpRole.USER,
                        McpRole.ASSISTANT,
                        McpRole.USER);
        assertThat(texts(result.messages())).containsExactly(
                "Classify: I love this product",
                "POSITIVE",
                "Classify: Delivery was a disaster",
                "NEGATIVE",
                "Classify: The support team was wonderful");
    }

    private static String text(McpPromptContent content) {
        return ((McpTextContent) content).text();
    }

    private static List<String> texts(List<McpPromptMessage> messages) {
        return messages.stream().map(message -> text(message.content())).toList();
    }
}