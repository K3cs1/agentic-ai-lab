### 04-01

# Manual Agentic Loop Implementation

This example demonstrates how to manually implement the core agentic loop without using an AI framework.
The application acts as the orchestrator between the user, the LLM, and the available 
tools and illustrates the fundamental mechanism behind agentic AI: the model autonomously decides 
when to invoke a tool, while the application is responsible for executing the tool and continuing the conversation.


# Prerequisites
- Ollama installed and running
- qwen2.5 model pulled locally