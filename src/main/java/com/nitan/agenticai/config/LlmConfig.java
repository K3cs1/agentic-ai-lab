package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.AgenticAssistant;
import com.nitan.agenticai.tools.CurrencyTools;
import com.nitan.agenticai.util.AiTool;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.DefaultToolExecutor;
import dev.langchain4j.service.tool.ToolExecutor;
import dev.langchain4j.service.tool.ToolProvider;
import dev.langchain4j.service.tool.ToolProviderResult;
import java.lang.reflect.Method;
import java.util.List;
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
        .logRequests(true)
        .logResponses(true)
        .temperature(0.2)
        .build();
  }

  @Bean
  public ToolProvider toolProvider(List<AiTool> tools) {
    ToolProviderResult.Builder builder = ToolProviderResult.builder();

    for (AiTool tool : tools) {
      for (Method method : tool.getClass().getDeclaredMethods()) {
        if (method.isAnnotationPresent(Tool.class)) {
          ToolSpecification spec = ToolSpecifications.toolSpecificationFrom(method);
          ToolExecutor executor = new DefaultToolExecutor(tool, method);
          builder.add(spec, executor);
        }
      }
    }

    ToolProviderResult result = builder.build();
    return request -> result;
  }
  @Bean
  AgenticAssistant agenticAssistant(ChatModel chatModel, CurrencyTools currencyTools, List<AiTool> tools) {
    return AiServices.builder(AgenticAssistant.class)
        .chatModel(chatModel)
        .toolProvider(toolProvider(tools))
        .build();
  }
}
