package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;

import com.nitan.agenticai.assistant.AgenticAssistant;
import dev.langchain4j.model.output.FinishReason;
import dev.langchain4j.service.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AgenticTest {

  @Autowired private AgenticAssistant agenticAssistant;

  @Test
  void test() {
    String message = "What is the exchange rate from USD to EUR?";

    prettyPrint("User", message);

    Result<String> response = agenticAssistant.handle(message);
    int lastToolExecution = response.toolExecutions().size()-1;
    if(FinishReason.TOOL_EXECUTION.equals(response.finishReason())) {
      prettyPrint("Assistant", response.toolExecutions().get(lastToolExecution).result());
    } else {
      prettyPrint("Assistant", response.finalResponse().aiMessage().text());
    }
  }
}
