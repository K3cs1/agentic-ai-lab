package com.nitan.agenticai.knowledge;

import org.springframework.stereotype.Component;

@Component
public class KnowledgeBaseService {

  /**
   * This method can return a context based on the message.
   * @param message
   * @return
   */
  public String retrieve(String message) {
    return """
                Employees in Bucharest can apply for vacation using the best HR Portal.
                Vacation requests require manager approval.
        """;
  }

}
