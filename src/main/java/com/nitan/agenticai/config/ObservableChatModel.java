package com.nitan.agenticai.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.*;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObservableChatModel implements ChatModel {

  private final ChatModel delegate;
  private final Tracer tracer;
  private final String modelName;
  private final ObjectMapper mapper = new ObjectMapper();

  public ObservableChatModel(ChatModel delegate, Tracer tracer, String modelName) {
    this.delegate = delegate;
    this.tracer = tracer;
    this.modelName = modelName;
  }

  @Override
  public ChatResponse chat(ChatRequest request) {

    Span span = Span.current();

    span.setAttribute("gen_ai.system", "ollama");
    span.setAttribute("gen_ai.operation.name", "chat");
    span.setAttribute("gen_ai.request.model", modelName);

    span.addEvent("gen_ai.content.prompt", Attributes.of(
        AttributeKey.stringKey("gen_ai.prompt"),
        serializeMessages(request.messages())
    ));

    ChatResponse response = delegate.chat(request);

    if (response.metadata() != null && response.metadata().tokenUsage() != null) {

      var usage = response.metadata().tokenUsage();

      span.setAttribute("gen_ai.usage.input_tokens", usage.inputTokenCount());
      span.setAttribute("gen_ai.usage.output_tokens", usage.outputTokenCount());
      span.setAttribute("gen_ai.usage.total_tokens", usage.totalTokenCount());
    }

    return response;
  }

  private String serializeMessages(List<ChatMessage> messages) {
    try {
      List<Map<String, String>> simplified = messages.stream()
          .map(m -> {
            String role;
            String content;

            if (m instanceof SystemMessage sm) {
              role = "system";
              content = sm.text();
            } else if (m instanceof UserMessage um) {
              role = "user";
              content = um.singleText();
            } else if (m instanceof AiMessage am) {
              role = "assistant";
              content = am.text() != null ? am.text() : "[tool call]";
            } else if (m instanceof ToolExecutionResultMessage tr) {
              role = "tool";
              content = tr.text();
            } else {
              role = "unknown";
              content = "";
            }

            return Map.of("role", role, "content", content != null ? content : "");
          })
          .toList();
      return mapper.writeValueAsString(simplified);
    } catch (JsonProcessingException e) {
      log.warn("Failed to serialize messages for tracing", e);
      return "[serialization error]";
    }
  }

}
