package com.nitan.agenticai;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.service.tool.ToolExecutionResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class McpTest {

    @Autowired
    private McpClient mcpClient;

    @Test
    void testMcpListTools() {
        List<ToolSpecification> tools = mcpClient.listTools();
        System.err.println("--->" + tools);
    }

    @Test
    void testMcpCallTool() {
        ToolExecutionResult result =
                mcpClient.executeTool(
                        ToolExecutionRequest.builder().name("git_log").arguments("{}").build());
        System.err.println("-->" + result);
        assertThat(result.isError()).isFalse();
        assertThat(result.resultText()).isNotBlank();
    }
}