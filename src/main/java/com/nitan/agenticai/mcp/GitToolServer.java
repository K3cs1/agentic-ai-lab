package com.nitan.agenticai.mcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.json.jackson2.JacksonMcpJsonMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class GitToolServer {

  public static void main(String[] args) {
    McpJsonMapper jsonMapper = new JacksonMcpJsonMapper(new ObjectMapper());
    StdioServerTransportProvider transport = new StdioServerTransportProvider(jsonMapper);

    McpSchema.Tool tool =
            McpSchema.Tool.builder()
                    .name("git_log")
                    .description("Show the last 5 commits of the Git repository where this server runs.")
                    .inputSchema(Map.of("type", "object"))
                    .build();

    SyncToolSpecification spec =
            SyncToolSpecification.builder()
                    .tool(tool)
                    .callHandler((exchange, request) -> gitLog())
                    .build();

    McpServer.sync(transport)
            .serverInfo("git-tool-server", "1.0.0")
            .tools(spec)
            .build();
  }

  private static McpSchema.CallToolResult gitLog() {
    try {
      Process process =
              new ProcessBuilder("git", "--no-pager", "log", "-n", "5", "--date=short", "--format=%h %ad %s")
                      .redirectErrorStream(true)
                      .start();
      String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
      boolean failed = process.waitFor() != 0;
      return McpSchema.CallToolResult.builder().addTextContent(output).isError(failed).build();
    } catch (Exception e) {
      return McpSchema.CallToolResult.builder().addTextContent(e.getMessage()).isError(true).build();
    }
  }
}