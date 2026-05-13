package com.nitan.agenticai.dto;

public record OllamaResponse(Message message) {

  public record Message(String role, String content) {

  }
}