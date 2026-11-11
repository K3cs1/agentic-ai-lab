package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.Assistant;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.List;

@Configuration
class LlmConfig {

  private static final String BASE_OLLAMA_URL = "http://localhost:11434";
  private static final String CHAT_MODEL = "qwen2.5";

  @Bean
  ChatModel chatLanguageModel() {
    return OllamaChatModel.builder()
        .baseUrl(BASE_OLLAMA_URL)
        .modelName(CHAT_MODEL)
        .temperature(0.2)
        .build();
  }

  @Bean(destroyMethod = "")
  McpTransport mcpTransport() {
    String javaBin = Path.of(System.getProperty("java.home"), "bin", "java").toString();
    String logbackConfig = Path.of("src/main/resources/logback.xml").toAbsolutePath().toString();

    return new StdioMcpTransport.Builder()
                    .command(
                            List.of(
                                    javaBin,
                                    "-Dlogback.configurationFile=" + logbackConfig,
                                    "-classpath",
                                    System.getProperty("java.class.path"),
                                    "com.nitan.agenticai.mcp.GitToolServer"))
                    .build();
  }

  @Bean(destroyMethod = "close")
  McpClient mcpClient(McpTransport transport) {
    return new DefaultMcpClient.Builder().transport(transport).build();
  }

  @Bean
  ToolProvider mcpToolProvider(McpClient mcpClient) {
    return McpToolProvider.builder().mcpClients(mcpClient).build();
  }

  @Bean
  Assistant assistant(ChatModel chatModel, ToolProvider mcpToolProvider) {
    return AiServices.builder(Assistant.class)
        .chatModel(chatModel)
        .toolProvider(mcpToolProvider)
        .build();
  }
}
