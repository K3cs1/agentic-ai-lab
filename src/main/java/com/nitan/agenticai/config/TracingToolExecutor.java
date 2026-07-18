package com.nitan.agenticai.config;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.service.tool.ToolExecutor;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TracingToolExecutor implements ToolExecutor {

  private final ToolExecutor delegate;
  private final Tracer tracer;
  private final String toolName;

  @Override
  public String execute(ToolExecutionRequest request, Object memoryId) {
    Span span = tracer.spanBuilder("tool." + toolName)
        .setSpanKind(SpanKind.INTERNAL)
        .startSpan();

    try (Scope scope = span.makeCurrent()) {
      span.setAttribute("gen_ai.tool.name", toolName);

      span.setAttribute("langfuse.observation.input", request.arguments());

      String result = delegate.execute(request, memoryId);

      span.setAttribute("langfuse.observation.output", result);

      return result;
    } catch (Exception e) {
      span.recordException(e);
      span.setStatus(StatusCode.ERROR);
      throw e;
    } finally {
      span.end();
    }
  }
}