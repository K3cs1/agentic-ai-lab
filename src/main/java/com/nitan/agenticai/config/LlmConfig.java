package com.nitan.agenticai.config;

import com.nitan.agenticai.assistant.RefundAssistant;
import com.nitan.agenticai.guardrails.RefundLimitGuardrail;
import com.nitan.agenticai.service.HumanApprovalService;
import com.nitan.agenticai.service.PromptService;
import com.nitan.agenticai.tools.OrderTools;
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
  ChatModel chatLanguageModel() {
    return OllamaChatModel.builder()
        .baseUrl(BASE_OLLAMA_URL)
        .modelName(MODEL)
        .temperature(0.2)
        .build();
  }

  @Bean
  RefundAssistant chatAssistant(ChatModel chatModel, PromptService promptService,
      HumanApprovalService humanApprovalService) {
    return AiServices.builder(RefundAssistant.class)
        .chatModel(chatModel)
        .tools(new OrderTools())
        .systemMessageProvider(context -> promptService.getPrompt("refundAssistant"))
        .outputGuardrails(new RefundLimitGuardrail(humanApprovalService))
        .build();
  }
}
