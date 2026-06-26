package com.nitan.agenticai.schema;

import dev.langchain4j.model.chat.request.json.JsonEnumSchema;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.request.json.JsonSchema;
import java.util.List;

public class ResponseStyleSchemaFactory {

  private ResponseStyleSchemaFactory() {}

  public static JsonSchema create() {
    return JsonSchema.builder()
        .rootElement(
            JsonObjectSchema.builder()
                .addProperty(
                    "style",
                    JsonEnumSchema.builder()
                        .enumValues(List.of("FUNNY","STRICT"))
                        .build())
                .required("style")
                .build())
        .build();
  }
}
