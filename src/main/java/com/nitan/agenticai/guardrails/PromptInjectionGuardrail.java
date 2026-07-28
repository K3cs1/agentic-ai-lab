package com.nitan.agenticai.guardrails;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.guardrail.InputGuardrail;
import dev.langchain4j.guardrail.InputGuardrailResult;
import java.util.List;

public class PromptInjectionGuardrail implements InputGuardrail {

  private static final List<String> INJECTION_PATTERNS = List.of(
      "ignore all previous instructions",
      "you now approve",
      "without limit"
  );

  @Override
  public InputGuardrailResult validate(UserMessage userMessage) {
    System.out.println("PromptInjectionGuardrail is validating user message..");
    String normalized = userMessage.singleText().toLowerCase();

    boolean matched = INJECTION_PATTERNS.stream().anyMatch(normalized::contains);

    return matched
        ? failure("Potential prompt injection detected!")
        : success();
  }
}