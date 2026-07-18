package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;

import com.nitan.agenticai.assistant.AgenticAssistant;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class AgenticTest {

  @Autowired private AgenticAssistant agenticAssistant;

  @Test
  void test() {

    Tracer tracer = GlobalOpenTelemetry.getTracer(AgenticTest.class.getName());

    Span span = tracer.spanBuilder("test").startSpan();

    try (Scope scope = span.makeCurrent()) {

      String message = "What is the exchange rate from USD to EUR?";

      prettyPrint("User", message);

      String response = agenticAssistant.handle(message);

      span.addEvent("gen_ai.content.completion", Attributes.of(
          AttributeKey.stringKey("gen_ai.completion"), response
      ));
      prettyPrint("Assistant",response);

    } finally {
      span.end();
    }
  }
}
