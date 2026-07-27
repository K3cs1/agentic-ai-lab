package com.nitan.agenticai.guardrails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nitan.agenticai.dto.RefundApprovalDecision;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.guardrail.OutputGuardrail;
import dev.langchain4j.guardrail.OutputGuardrailResult;

public class RefundLimitGuardrail implements OutputGuardrail {

  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final double LIMIT = 50.0;

  @Override
  public OutputGuardrailResult validate(AiMessage responseFromLLM) {
    try {
      RefundApprovalDecision decision =
          objectMapper.readValue(responseFromLLM.text(), RefundApprovalDecision.class);
      if (decision.approved() && decision.requestedAmount() != null && decision.requestedAmount() > LIMIT) {
        return  failure("The refund requested amount of %.2f EUR exceeds the allowed limit of %.2f EUR."
                .formatted(decision.requestedAmount(), LIMIT));
      }

      return success();

    } catch (JsonProcessingException e) {
      return reprompt("Malformed response",
          "Your response must be valid JSON matching the expected schema.");
    }
  }
}