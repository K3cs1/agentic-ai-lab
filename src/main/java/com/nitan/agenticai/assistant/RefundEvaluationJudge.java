package com.nitan.agenticai.assistant;

import com.nitan.agenticai.dto.EvaluationResult;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface RefundEvaluationJudge {

  @UserMessage("""
      A customer sent this request to a refund assistant:
      REQUEST: {{request}}

      The assistant's decision was:
      APPROVED: {{approved}}
      AMOUNT: {{amount}}
      REASON GIVEN: {{reason}}
      ESCALATED TO HUMAN: {{escalated}}

      Evaluate this on three dimensions, each scored 0-100 with a short justification:

      1. REASON QUALITY: Is the stated reason a clear, accurate explanation
         for the decision, given the request and the 50 EUR policy limit?

      2. ESCALATION APPROPRIATENESS: Given how ambiguous or borderline the
         request was, was escalating (or not escalating) to a human the
         right call? A clear-cut request should not need escalation; a
         genuinely ambiguous one should.

      3. TONE: Is the reason phrased in a way that's respectful and
         appropriate for a customer, even when declining or escalating?

      Respond with scores and justifications for all three dimensions.
      """)
  EvaluationResult evaluate(@V("request") String request, @V("approved") boolean approved,
                            @V("amount") Double amount, @V("reason") String reason,
                            @V("escalated") boolean escalated);
}