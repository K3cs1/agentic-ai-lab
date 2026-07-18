package com.nitan.agenticai.service;

import com.nitan.agenticai.dto.Prompt;
import com.nitan.agenticai.repository.PromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptService {

  private final PromptRepository repository;

  public String getSystemPrompt(String promptKey) {
    return repository
        .findByKey("system/"+promptKey)
        .map(Prompt::content)
        .orElseThrow(() -> new IllegalStateException("Prompt not found: " + promptKey));
  }

  public String getToolDescription(String promptKey) {
    return repository
        .findByKey("tools/"+promptKey)
        .map(Prompt::content)
        .orElseThrow(() -> new IllegalStateException("Prompt not found: " + promptKey));
  }
}
