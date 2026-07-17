package com.nitan.agenticai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class RawMcpClient {

    private final HttpClient http = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();
    private final String baseUrl;
    private String sessionId;

    public RawMcpClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void initialize() throws Exception {
        var body = Map.of(
            "jsonrpc", "2.0",
            "id", 1,
            "method", "initialize",
            "params", Map.of(
                "protocolVersion", "2025-06-18",
                "capabilities", Map.of(),
                "clientInfo", Map.of("name", "lab-raw-client", "version", "1.0.0")
            )
        );

        HttpResponse<String> response = post(body);

        this.sessionId = response.headers().firstValue("Mcp-Session-Id")
            .orElseThrow(() -> new IllegalStateException("No session id returned"));

        // Spec requires an "initialized" notification before further calls
        //post(Map.of("jsonrpc", "2.0", "method", "notifications/initialized"));
    }

    public String listTools() throws Exception {
        var body = Map.of("jsonrpc", "2.0", "id", 2, "method", "tools/list", "params", Map.of());
        return post(body).body();
    }

    public String callTool(String toolName, Map<String, Object> arguments) throws Exception {
        var body = Map.of(
            "jsonrpc", "2.0",
            "id", 3,
            "method", "tools/call",
            "params", Map.of("name", toolName, "arguments", arguments)
        );
        return post(body).body();
    }

    private HttpResponse<String> post(Map<String, Object> body) throws Exception {
        var requestBuilder = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl))
            .header("Content-Type", "application/json")
            .header("Accept", "application/json, text/event-stream")
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body)));

        if (sessionId != null) {
            requestBuilder.header("Mcp-Session-Id", sessionId);
        }

        return http.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
    }

    public static void main(String[] args) throws Exception {
        RawMcpClient client = new RawMcpClient("http://localhost:3001/mcp");
        client.initialize(); // ⭐

        String toolsRaw = client.listTools();// ⭐
        JsonNode toolsResponse = mapper.readTree(parseSSE(toolsRaw));
        System.out.println("Tools (parsed): " + toolsResponse.toPrettyString());

        String toolName = toolsResponse.get("result").get("tools").get(0).get("name").asText();

        String callToolResult = client.callTool(toolName, Map.of(
            "fromCurrency", "USD", "toCurrency", "EUR"
        ));// ⭐
        JsonNode callToolResponse = mapper.readTree(parseSSE(callToolResult));

        System.out.println("Call tool response: " + callToolResponse.toPrettyString());

    }

    private static String parseSSE(String sse){
        return sse.lines()
            .filter(line -> line.startsWith("data:"))
            .findFirst()
            .map(line -> line.substring("data:".length()).trim())
            .orElseThrow(() -> new IllegalStateException("No data: line in response"));
    }
}