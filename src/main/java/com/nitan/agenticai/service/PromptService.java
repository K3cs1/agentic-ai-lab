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
        .findByKey(promptKey)
        .map(Prompt::content)
        .orElseThrow(() -> new IllegalStateException("Prompt not found: " + promptKey));
  }
}
