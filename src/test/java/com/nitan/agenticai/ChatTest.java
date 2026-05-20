package com.nitan.agenticai;

import static org.assertj.core.api.Assertions.assertThat;

import com.nitan.agenticai.assistant.ChatAssistant;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatTest {

  @Autowired
  ChatAssistant chatAssistant;
  @Autowired ChatModel judgeModel;

  static Stream<Arguments> goldenDataset() {
    return Stream.of(
        Arguments.of(
            "How many days of vacation is an employee entitled to?",
            "An employee is entitled to 21 days of vacation per year.Can be carried over up to 18 months before they expire."),
        Arguments.of(
            "What is the local tehchnology support number for the Hexagon Office?",
            "+40 372 286 030"),
        Arguments.of(
            "How long it takes to get an Amex?",
            "Once you've applied you will receive your card in 3-4 weeks."),
        Arguments.of(
            "What is the referral bonus for a successfull hire on Corporate Functions?",
            "3,600 lei gross for each successful hire at levels 12–9, or 6,000 lei gross for hires at level 8 and above"));
  }

  @ParameterizedTest
  @MethodSource("goldenDataset")
  void responseShouldBeSemanticallySimilar(String input, String expected) {
    String agentResponse = chatAssistant.answer(input);

    String judgePrompt =
        """
        You are a semantic evaluator.

        Compare the EXPECTED answer and the ACTUAL answer.
        They do not need to be identical, but they must mean the same thing.

        Reply with 1 if they are semantically equivalent.
        Reply with 0 if they are not.

        Single digit only. No explanation.

        Expected: %s
        Actual:   %s
        """
            .formatted(expected, agentResponse);

    System.out.println("Agent answer: " + agentResponse);
    System.out.println("Expected answer: " + expected);

    int score =
        AiServices.builder(Judge.class)
            .chatModel(judgeModel)
            .systemMessage(judgePrompt)
            .build()
            .evaluate(agentResponse);

    assertThat(score)
        .as("Response to '%s' should be semantically equivalent to '%s'", input, expected)
        .isEqualTo(1);
  }
}
