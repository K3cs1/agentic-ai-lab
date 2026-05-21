### 01-04

# Using langchain4j's AiServices

By using AiServices, we can define Java interfaces that are automatically implemented at runtime to 
interact with LLMs such as OpenAI or Ollama.

When combined with dependency injection frameworks like Spring, these AI service implementations can 
be managed as standard beans, making it straightforward to integrate conversational AI capabilities 
into Java applications.

# Prerequisites

- Ollama installed and running
- qwen2.5 model pulled locally