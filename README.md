### 05-09

# Langfuse tracing with OpenTelemetry
This example shows how to capture LLM call traces using OpenTelemetry and ship them to Langfuse for observability.

# Prerequisites
- Ollama installed and running
- qwen2.5 model pulled locally
- langfuse and the observability infrastructure up(see branch ai-observability-stack for
    instructions)

Run the test with an attached OTel Java agent and by specifying the OTLP endpoint:

-javaagent:/Users/nicusor.nita/.m2/repository/io/opentelemetry/javaagent/opentelemetry-javaagent/2.27.0/opentelemetry-javaagent-2.27.0.jar
-Dotel.exporter.otlp.endpoint=http://localhost:4318

Note: adjust the -javaagent path to your local Maven repo location, and the
otlp.endpoint if your OTel Collector isn't running on localhost:4318.