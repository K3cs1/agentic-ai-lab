package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.Assistant;
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
    private static final String CHAT_MODEL = "qwen2.5";

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
    Assistant assistant(ChatModel chatModel) {
        return AiServices.builder(Assistant.class)
                .chatModel(chatModel)
                .build();
    }
}