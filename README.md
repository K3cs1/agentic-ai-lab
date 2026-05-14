### 03-01

# Halucinating helpful assistant.

Returning to stateless assistants by implementing a stateless assistant using langchain4j. In the 
previous session the focus has been on memory, but memory is not knowledge. 

This session is about giving the agent access to knowledge, and then letting it use that knowledge 
to answer questions.

This version is obviously trying to be helpful, but it is halucinating helpfulness, as it is not 
actually connected to any knowledge source. 

# Prerequisites

- Ollama installed and running
- qwen2.5 model pulled locally