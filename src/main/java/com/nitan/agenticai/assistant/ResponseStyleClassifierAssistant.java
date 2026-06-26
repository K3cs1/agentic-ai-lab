package com.nitan.agenticai.assistant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nitan.agenticai.domain.ResponseStyle;
import com.nitan.agenticai.schema.ResponseStyleSchemaFactory;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.chat.request.ResponseFormatType;
import dev.langchain4j.model.chat.response.ChatResponse;
import org.springframework.stereotype.Component;

@Component
public class ResponseStyleClassifierAssistant {

  private final ChatModel model;
  private final ObjectMapper objectMapper;

  public ResponseStyleClassifierAssistant(ChatModel model, ObjectMapper objectMapper) {
    this.model = model;
    this.objectMapper = objectMapper;
  }

  public ResponseStyle classify(String input) {

    ResponseFormat responseFormat =
        ResponseFormat.builder()
            .type(ResponseFormatType.JSON)
            .jsonSchema(ResponseStyleSchemaFactory.create())
            .build();

    ChatRequest request =
        ChatRequest.builder()
            .messages(
                SystemMessage.from(
                    """
                    Classify the tone of the input text.
                    If it seems laconic and to the point, classify as STRICT.
                    If it seems humorous and light, classify as FUNNY.
                    """),
                UserMessage.from(input))
            .responseFormat(responseFormat)
            .build();

    ChatResponse response = model.chat(request);

    String json = response.aiMessage().text();

    try {
      return objectMapper.readValue(json, ResponseStyle.class);
    } catch (Exception e) {
      throw new RuntimeException("Invalid JSON response: " + json, e);
    }
  }
}
