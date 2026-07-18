package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.AgenticAssistant;
import com.nitan.agenticai.service.PromptService;
import com.nitan.agenticai.util.AiTool;
import dev.langchain4j.agent.tool.SearchBehavior;
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
import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LlmConfig {

  private static final String BASE_OLLAMA_URL = "http://localhost:11434";
  private static final String MODEL = "qwen2.5";

  @Bean
  ChatModel chatLanguageModel() {
    return OllamaChatModel.builder()
        .baseUrl(BASE_OLLAMA_URL)
        .modelName(MODEL)
        .logRequests(true)
        .temperature(0.8)
        .build();
  }

  @Bean
  public ToolProvider toolProvider(List<AiTool> tools, PromptService promptService) {

    return request -> {
      ToolProviderResult.Builder builder = ToolProviderResult.builder();

      for (Object tool : tools) {
        Class<?> targetClass = AopUtils.getTargetClass(tool);

        for (Method method : targetClass.getDeclaredMethods()) {
          if (!method.isAnnotationPresent(Tool.class)) {
            continue;
          }

          ToolSpecification baseSpec = ToolSpecifications.toolSpecificationFrom(method);
          String externalDescription = promptService.getToolDescription(baseSpec.name());
          System.out.println("External description for tool " + baseSpec.name() + ": " + externalDescription);
          ToolSpecification spec = ToolSpecification.builder()
              .name(baseSpec.name())
              .description(externalDescription)
              .parameters(baseSpec.parameters())
              .addMetadata(ToolSpecification.METADATA_SEARCH_BEHAVIOR, SearchBehavior.SEARCHABLE)
              .build();

          ToolExecutor executor = new DefaultToolExecutor(tool, method);
          builder.add(spec, executor);
        }
      }

      return builder.build();
    };
  }

  @Bean
  AgenticAssistant chatAssistant(ChatModel chatModel, List<AiTool> tools,
      PromptService promptService) {
    return AiServices.builder(AgenticAssistant.class)
        .chatModel(chatModel)
        .systemMessageProvider(context -> promptService.getSystemPrompt("my-agentic-assistant"))
        .toolProvider(toolProvider(tools, promptService))
        .build();
  }
}
