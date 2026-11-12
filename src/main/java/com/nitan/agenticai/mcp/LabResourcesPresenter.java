package com.nitan.agenticai.mcp;

import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.McpResourceTemplate;
import dev.langchain4j.mcp.resourcesastools.DefaultMcpResourcesAsToolsPresenter;
import dev.langchain4j.service.tool.ToolExecutor;

import java.util.List;

public class LabResourcesPresenter extends DefaultMcpResourcesAsToolsPresenter {
  public LabResourcesPresenter(String mcpServerKey) {
    super(DEFAULT_NAME_OF_GET_RESOURCE_TOOL,
            DEFAULT_DESCRIPTION_OF_GET_RESOURCE_TOOL,
            mcpServerKey,
            """
                    The URI of the resource to get.
                    Values in curly braces are placeholders for the resource URI, and should be replaced 
                    with actual values from the context before calling get resource.
                    Example: ticket://{id} becomes ticket://TCK-n.
            """,
            DEFAULT_NAME_OF_LIST_RESOURCES_TOOL,
            DEFAULT_DESCRIPTION_OF_LIST_RESOURCES_TOOL);
  }

  @Override
  public ToolExecutor createListResourcesExecutor(List<McpClient> mcpClients) {
    ToolExecutor defaultList = super.createListResourcesExecutor(mcpClients);
    return (request, memoryId) -> {
      StringBuilder out = new StringBuilder(defaultList.execute(request, memoryId));
      for (McpClient client : mcpClients) {
        for (McpResourceTemplate template : client.listResourceTemplates()) {
          out.append("\nserver=").append(client.key())
              .append(" template=").append(template.uriTemplate())
              .append(" - ").append(template.description());
        }
      }
      return out.toString();
    };
  }
}