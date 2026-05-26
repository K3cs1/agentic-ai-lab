package com.nitan.agenticai.tools;

import dev.langchain4j.agent.tool.Tool;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HumanInputTool {

  private final Scanner scanner = new Scanner(System.in);

  @Tool("Ask the human user to provide missing information needed to answer the question")
  public String askHuman(String question) {
    System.out.println("\n\u001B[33m🤖 " + question + "\u001B[0m");
    System.out.print("\u001B[36m👤 Your answer: \u001B[0m");
    return scanner.nextLine();
  }
}
