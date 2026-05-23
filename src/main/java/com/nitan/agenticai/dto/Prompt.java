package com.nitan.agenticai.dto;

public record Prompt(
    Long id,
    String promptKey,
    int version,
    String content
) {}