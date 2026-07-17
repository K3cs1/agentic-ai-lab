### 05-03

# Tool search strategy

This example demonstrates how to scale tool selection beyond sending every tool's full specification 
to the model on every request.
As a tool ecosystem grows — sending dozens of tool schemas on every call bloats the prompt, 
increases token cost and latency, and can confuse smaller models into picking the wrong tool. 

# Prerequisites
- Ollama installed and running
- qwen2.5 model pulled locally