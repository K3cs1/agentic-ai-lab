package com.nitan.agenticai.mcp;

import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.service.tool.ToolExecutor;
import dev.langchain4j.service.tool.ToolProvider;
import dev.langchain4j.service.tool.ToolProviderRequest;
import dev.langchain4j.service.tool.ToolProviderResult;
import dev.langchain4j.store.embedding.CosineSimilarity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SemanticMcpToolProvider implements ToolProvider {

    private final ToolProvider delegate;
    private final McpClient mcpClient;
    private final EmbeddingModel embeddingModel;
    private final Map<String, Embedding> toolEmbeddings;
    private final double threshold;

    public SemanticMcpToolProvider(
            McpClient mcpClient,
            EmbeddingModel embeddingModel,
            double threshold) {
        this.delegate = McpToolProvider.builder().mcpClients(mcpClient).build();
        this.mcpClient = mcpClient;
        this.embeddingModel = embeddingModel;
        this.threshold = threshold;

        this.toolEmbeddings = new ConcurrentHashMap<>();

        // Retrieve tools and calculate their embeddings once.
        mcpClient.listTools().forEach(tool -> {
            String description = tool.name() + " " + tool.description();

            Embedding embedding = embeddingModel.embed(description).content();

            toolEmbeddings.put(tool.name(), embedding);
        });
        System.out.println("SemanticMcpToolProvider initialized with " + toolEmbeddings.size() + " tools.");
    }

    @Override
    public ToolProviderResult provideTools(ToolProviderRequest request) {
        Embedding queryEmbedding = embeddingModel.embed(request.userMessage().singleText()).content();

        ToolProviderResult.Builder selected = ToolProviderResult.builder();
        delegate
                .provideTools(request)
                .tools()
                .forEach(
                        (ToolSpecification spec, ToolExecutor executor) -> {
                            Embedding toolEmbedding = toolEmbeddings.get(spec.name());
                            if (toolEmbedding != null
                                    && CosineSimilarity.between(queryEmbedding, toolEmbedding) >= threshold) {
                                selected.add(spec, executor);
                            }
                        });
        return selected.build();
    }
}