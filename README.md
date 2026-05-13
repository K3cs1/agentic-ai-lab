### 01-01 

# Stateless Chat Using the Ollama Library

This is a basic blocking chat simulation using the Ollama library.
It demonstrates a simple stateless chat communication where users can send messages and receive responses from the chatbot.

The chatbot generates responses based on the input messages, and the communication is done in a blocking manner, meaning that the user has to wait for the entire response to be generated before seeing it.

Depending on the size of the response, this can lead to a less interactive experience, as users won't see any output until the entire response is ready.

# Prerequisites

- Ollama installed and running
- qwen2.5 model pulled locally