package com.nitan.agenticai.assistant;

import dev.langchain4j.service.SystemMessage;

public interface ResponseStyleClassifierAssistant {

  @SystemMessage(
      """
        Classify the tone of the input text.
        Return ONLY valid JSON matching this schema:
              {
                "type": "object",
                "properties": {
                  "style": {
                    "type": "string",
                    "enum": ["FUNNY", "STRICT"]
                  }
                },
                "required": ["style"],
                "additionalProperties": false
              }
    """)
  String classify(String input);
}
