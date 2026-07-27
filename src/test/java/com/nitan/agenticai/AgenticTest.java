package com.nitan.agenticai;

import static com.nitan.agenticai.util.Util.prettyPrint;

import com.nitan.agenticai.assistant.RefundAssistant;
import com.nitan.agenticai.dto.RefundApprovalDecision;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


class AgenticTest {



  public static void main(String[] args) throws Exception {

    ConfigurableApplicationContext ctx =
        new SpringApplicationBuilder(AgenticAiLabApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

    RefundAssistant refundAssistant = ctx.getBean(RefundAssistant.class);

    String message = "Hi, I'd like a refund for my order 4471.";
    prettyPrint("User", message);
    RefundApprovalDecision decision = refundAssistant.handleRefundRequest(message);
    System.out.println(decision);
    prettyPrint("Assistant", decision.approved() ? "Approved" : "Denied");
  }
}