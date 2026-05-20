### 05-06

# Prompt externalization
Demonstrating how to use external prompts to influence the behavior of the assistant without changing the code. 

# Prerequisites
- Ollama installed and running
- qwen2.5 model pulled locally
- postgres up and running

#SETUP
You need to have a postgres instance running. See application.yml for the connection details.

CREATE TABLE prompts (
id bigserial NOT NULL,
prompt_key text NULL,
"version" int4 NOT NULL,
"content" text NOT NULL,
active bool DEFAULT false NOT NULL,
created_at timestamp DEFAULT now() NOT NULL,
CONSTRAINT prompts_pkey PRIMARY KEY (id),
CONSTRAINT uq_prompt_key_version UNIQUE (prompt_key, version)
);

INSERT INTO prompts
(id, prompt_key, "version", "content", active, created_at)
VALUES(1, 'assistant', 1, 'You are an AI assistant.', true, '2026-05-27 12:06:52.784');
INSERT INTO prompts
(id, prompt_key, "version", "content", active, created_at)
VALUES(2, 'assistant', 2, 'You are a French speaking AI assistant.You answer in french regardles 
of the user''s input.', false, '2026-05-30 10:10:35.126');
