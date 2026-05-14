### 02-06

# Postgres chat memory store.

Using a Postgres chat memory store implementation to persist the conversation history in a stateful
assistant implementation.

This version includes an example of how to set up a Postgres database to store the conversation history
and configure the chat memory store to use the Postgres implementation, allowing for scalable and persistent
memory management in the assistant. The database stores the full conversation history, but MessageWindowChatMemory
controls how much of that history is included in the prompt sent to the model.

# Prerequisites

- Ollama installed and running
- qwen2.5 model pulled locally
- ***postgres database running and accessible on your localhost***

## Initial Setup
Check application.yml for the datasource connection details.

Then create the table chat_memory:

CREATE TABLE chat_memory (
memory_id TEXT NOT NULL,
message_index INT NOT NULL,
role TEXT NOT NULL,
content TEXT NOT NULL,
PRIMARY KEY (memory_id, message_index)
);
