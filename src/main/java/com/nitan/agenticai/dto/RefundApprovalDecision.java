package com.nitan.agenticai.dto;

public record RefundApprovalDecision(boolean approved, int orderId, Double orderAmount,
                                     String reason) {

}
