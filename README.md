### 04-02

# Manual Agentic Loop with final answer tool

This example introduces a custom @FinalAnswer annotation. 
When a tool annotated with @FinalAnswer is invoked, the application returns its result directly to 
the user, skipping the final LLM call. This optimization reduces both latency and cost while preserving 
the manual orchestration of the agentic loop.

# Prerequisites
- Ollama installed and running
- qwen2.5 model pulled locally