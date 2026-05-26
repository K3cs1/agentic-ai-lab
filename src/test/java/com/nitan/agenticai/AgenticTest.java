package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;

import com.nitan.agenticai.assistant.AgenticAssistant;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

class AgenticTest {

  public static void main(String[] args) {
    ConfigurableApplicationContext ctx =
        new SpringApplicationBuilder(AgenticAiLabApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

    AgenticAssistant assistant = ctx.getBean(AgenticAssistant.class);

    String message = "What would be the weather in my city tomorrow?";

    prettyPrint("User message",message);

    String response = assistant.handle(message);

    prettyPrint("Final response",response);

    ctx.close();
  }
}
