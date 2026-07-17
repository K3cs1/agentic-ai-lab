package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.AgenticAssistant;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LlmConfig {

  private static final String BASE_OLLAMA_URL = "http://localhost:11434";
  private static final String MODEL = "qwen2.5";

  @Bean
  ChatModel chatModel() {
    return OllamaChatModel.builder()
        .baseUrl(BASE_OLLAMA_URL)
        .modelName(MODEL)
        .temperature(0.2)
        .build();
  }

  @Bean
  McpToolProvider toolProvider() {
    McpTransport transport = StreamableHttpMcpTransport.builder()
        .url("http://localhost:3001/mcp")
        .logRequests(true)
        .logResponses(true)
        .build();

    McpClient client = DefaultMcpClient.builder()
        .key("my-server")
        .transport(transport)
        .build();

    return McpToolProvider.builder()
        .mcpClients(client)
        .build();
  }

  @Bean
  AgenticAssistant agenticAssistant(ChatModel chatModel) {
    return AiServices.builder(AgenticAssistant.class)
        .chatModel(chatModel)
        .toolProvider(toolProvider())
        .build();
  }
}
