package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.Assistant;
import com.nitan.agenticai.mcp.LabResourcesPresenter;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LlmConfig {

    private static final String BASE_OLLAMA_URL = "http://localhost:11434";
    private static final String CHAT_MODEL = "qwen3.5:9b";

    @Bean
    ChatModel chatLanguageModel() {
        return OllamaChatModel.builder()
                .baseUrl(BASE_OLLAMA_URL)
                .modelName(CHAT_MODEL)
                .temperature(0.2)
                .logRequests(true)
                .build();
    }

    @Bean(destroyMethod = "")
    McpTransport mcpTransport() {
        return StreamableHttpMcpTransport.builder()
                .url("http://localhost:3001/mcp")
                .logRequests(true)
                .build();
    }

    @Bean(destroyMethod = "close")
    McpClient mcpClient(McpTransport transport) {
        return new DefaultMcpClient.Builder().transport(transport).build();
    }

    @Bean
    ToolProvider mcpToolProvider(McpClient mcpClient) {
        return McpToolProvider.builder()
                .mcpClients(mcpClient)
                .resourcesAsToolsPresenter(new LabResourcesPresenter(mcpClient.key()))
                .build();
    }

    @Bean
    Assistant assistant(ChatModel chatModel, McpClient mcpClient, McpTransport mcpTransport) {
        return AiServices.builder(Assistant.class)
                .chatModel(chatModel)
                .toolProvider(mcpToolProvider(mcpClient))
                .build();
    }
}