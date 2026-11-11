package com.nitan.agenticai;

import dev.langchain4j.mcp.client.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class McpTest {
    @Autowired
    private McpClient mcpClient;

    @Test
    void listResources() {
        List<McpResource> resources = mcpClient.listResources();
        System.out.println("Resources: " + resources);
    }

    @Test
    void listResourceTemplates() {
        List<McpResourceTemplate> templates = mcpClient.listResourceTemplates();
        System.out.println("Resource templates: " + templates);
    }

    @Test
    void readResource() {
        McpReadResourceResult result = mcpClient.readResource("policy://vacation");
        System.out.println("policy://vacation -->\n" + text(result));
    }

    @Test
    void readResourceTemplate() {
        McpReadResourceResult result = mcpClient.readResource("ticket://TCK-1");
        System.out.println("ticket://TCK-1 --> " + text(result));
    }

    @Test
    void readUnknownTicket() {
        try {
            mcpClient.readResource("ticket://TCK-999");
            System.out.println("ticket://TCK-999 --> unexpectedly succeeded");
        } catch (RuntimeException e) {
            System.out.println("ticket://TCK-999 --> failed as expected: " + e.getMessage());
        }
    }

    private static String text(McpReadResourceResult result) {
        McpResourceContents contents = result.contents().get(0);
        return ((McpTextResourceContents) contents).text();
    }
}