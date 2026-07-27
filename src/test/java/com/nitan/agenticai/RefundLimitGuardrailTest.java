package com.nitan.agenticai;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nitan.agenticai.guardrails.RefundLimitGuardrail;
import com.nitan.agenticai.service.HumanApprovalService;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.guardrail.OutputGuardrailResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RefundLimitGuardrailTest {

  @Mock
  HumanApprovalService humanApprovalService;

  RefundLimitGuardrail guardrail;

  @BeforeEach
  void setUp() {
    guardrail = new RefundLimitGuardrail(humanApprovalService);
  }

  @Test
  void allowsAmountWithinLimit() {
    AiMessage response = AiMessage.from(
        """
        {"approved": true, "orderId": 4471, "orderAmount": 30.0, "reason": "test"}
        """);

    OutputGuardrailResult result = guardrail.validate(response);

    verify(humanApprovalService, never()).requestApproval(anyString());
    assertTrue(result.isSuccess());
  }

  @Test
  void escalatesAndSucceedsWhenHumanApproves() {
    AiMessage response = AiMessage.from(
        """
        {"approved": true, "orderId": 4471, "orderAmount": 55.0, "reason": "test"}
        """);
    when(humanApprovalService.requestApproval(anyString())).thenReturn(true);

    OutputGuardrailResult result = guardrail.validate(response);

    verify(humanApprovalService).requestApproval("test");
    assertTrue(result.isSuccess());
  }

  @Test
  void escalatesAndFailsWhenHumanRejects() {
    AiMessage response = AiMessage.from(
        """
        {"approved": true, "orderId": 4471, "orderAmount": 55.0, "reason": "test"}
        """);
    when(humanApprovalService.requestApproval(anyString())).thenReturn(false);

    OutputGuardrailResult result = guardrail.validate(response);

    assertFalse(result.isSuccess());
  }

  @Test
  void doNotEscalateWhenModelSelfDeniesAndAmountOverLimit() {
    AiMessage response = AiMessage.from(
        """
        {"approved": false, "orderId": 4471, "orderAmount": 51.0, "reason": "Exceeds limit"}
        """);

    guardrail.validate(response);

    verify(humanApprovalService, never()).requestApproval("Exceeds limit");
  }
}