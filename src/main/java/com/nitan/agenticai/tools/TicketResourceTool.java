package com.nitan.agenticai.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.McpTextResourceContents;
import org.springframework.stereotype.Component;

@Component
public class TicketResourceTool {

    private final McpClient mcpClient;

    public TicketResourceTool(McpClient mcpClient) {
        this.mcpClient = mcpClient;
    }

    @Tool("""
          Retrieves a support ticket by its ID.
          Use this when the user asks about a specific ticket.
          """)
    public String getTicket(
            @P("Ticket identifier, for example TCK-1") String id) {

        String uri = "ticket://" + id;

        return mcpClient.readResource(uri)
                .contents()
                .stream()
                .map(content -> {
                    if (content instanceof McpTextResourceContents text) {
                        return text.text();
                    }
                    return "[Unsupported ticket resource content]";
                })
                .collect(java.util.stream.Collectors.joining("\n"));
    }
}