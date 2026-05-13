### 02-06

# Using a token window strategy

Using ChatMemoryProvider with a token window strategy to manage the conversation history in a stateful 
assistant implementation. 

This version includes an example of how to configure ChatMemoryProvider to limit the conversation history 
based on the number of tokens rather than the number of messages, providing a more fine-grained control 
over the memory usage while still maintaining the necessary context for the assistant to generate relevant responses.


# Prerequisites

- Ollama installed and running
- qwen2.5 model pulled locally
