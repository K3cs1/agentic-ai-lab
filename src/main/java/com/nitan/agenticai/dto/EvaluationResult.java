package com.nitan.agenticai.dto;

public record EvaluationResult(
    int reasonQualityScore,
    String reasonQualityJustification,
    int escalationOpportunityScore,
    String escalationJustification,
    int toneScore,
    String toneJustification
) {}