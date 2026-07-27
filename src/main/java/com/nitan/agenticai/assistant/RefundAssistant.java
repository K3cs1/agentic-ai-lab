package com.nitan.agenticai.assistant;

import com.nitan.agenticai.dto.RefundApprovalDecision;

public interface RefundAssistant {
  RefundApprovalDecision handleRefundRequest(String message);
}