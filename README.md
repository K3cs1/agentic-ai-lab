### 04-04

# Agentic assistant with final-answer tools

This example demonstrates how to configure a tool with a final-answer return behavior using LangChain4j.

By default, after a tool is executed, LangChain4j sends the tool result back to the LLM so it can generate 
the final response. However, some tools already produce a complete answer to the user's request, making the 
final LLM call unnecessary.

By configuring the tool with a final-answer return behavior, LangChain4j returns the tool's output directly 
to the application, skipping the final LLM call. This reduces both latency and cost while allowing the framework 
to manage the agentic loop automatically.

# Prerequisites
- Ollama installed and running
- qwen2.5 model pulled locally