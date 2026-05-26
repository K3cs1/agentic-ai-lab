package com.nitan.agenticai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ManualAgenticLoopDemo {

  private static final String OLLAMA_URL = "http://localhost:11434/api/chat";
  private static final ObjectMapper mapper = new ObjectMapper();

  public static void main(String[] args) throws Exception {

    String userMessage = "What is the exchange rate from USD to EUR";

    // TOOL DEFINITION
    String toolSpec = """
        {
          "type": "function",
          "function": {
            "name": "getExchangeRate",
            "description": "Get the current exchange rate between two currency codes, e.g. USD to EUR.",
            "parameters": {
              "type": "object",
              "properties": {
                "fromCurrency": { "type": "string" },
                "toCurrency": { "type": "string" }
              },
              "required": ["fromCurrency", "toCurrency"]
            }
          }
        }
        """;

    // LLM CALL
    String request = """
            {
              "model": "qwen2.5",
              "messages": [
                { "role": "user", "content": "%s" }
              ],
              "tools": [%s],
              "stream": false
            }
            """.formatted(userMessage,toolSpec);

    System.out.println("Sending message to the LLM:  " + request);

    String response = post(request);

    JsonNode responseJson = mapper.readTree(response);

    System.out.println("LLM response:");
    System.out.println(
        mapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(responseJson)
    );

    JsonNode message = responseJson.get("message");

    // CHECK IF LLM WANTS TO CALL A TOOL
    JsonNode toolCalls = message.get("tool_calls");

    if (toolCalls == null || toolCalls.isEmpty()) {
      System.out.println("LLM answered directly: " + message.get("content").asText());
      return;
    }

    // EXECUTE THE TOOL
    JsonNode toolCall = toolCalls.get(0);

    String toolName = toolCall.get("function").get("name").asText();

    String fromCurrency = toolCall.get("function").get("arguments").get("fromCurrency").asText();
    String toCurrency = toolCall.get("function").get("arguments").get("toCurrency").asText();

    Method method = ManualAgenticLoopDemo.class
        .getDeclaredMethod(toolName, String.class, String.class);

    Double toolResult = (Double) method.invoke(null, fromCurrency, toCurrency);

    // SEND THE TOOL RESULT BACK TO THE LLM
    request = """
        {
          "model": "qwen2.5",
          "messages": [
            {
              "role": "user",
              "content": "%s"
            },
            {
              "role": "assistant",
              "content": "",
              "tool_calls": %s
            },
            {
              "role": "tool",
              "name": "%s",
              "content": "%s"
            }
          ],
          "tools": [%s],
          "stream": false
        }
        """.formatted(
        userMessage,
        mapper.writeValueAsString(toolCalls),
        toolName,
        toolResult,
        toolSpec
    );

    System.out.println("Sending tool result back to LLM:  " + request);
    String secondResponse = post(request);

    responseJson = mapper.readTree(secondResponse);

    System.out.println("Final response:");
    System.out.println(
        mapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(responseJson)
    );

  }

  public static double getExchangeRate(String fromCurrency, String toCurrency) {
    System.out.println("Fetching exchange rate "+ fromCurrency +" ->"+ toCurrency);
    // Fake but fixed rate so you can verify correctness deterministically
    if ("USD".equalsIgnoreCase(fromCurrency) && "EUR".equalsIgnoreCase(toCurrency)) {
      return 0.87;
    }
    return 1.0;
  }

  private static String post(String body) throws Exception {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(OLLAMA_URL))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(body))
        .build();
    return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
  }
}