package com.nitan.agenticai.config;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostgresChatMemoryStore implements ChatMemoryStore {

  private final JdbcTemplate jdbc;

  public PostgresChatMemoryStore(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public List<ChatMessage> getMessages(Object memoryId) {
    return jdbc.query(
        """
                SELECT role, content
                FROM chat_memory
                WHERE memory_id = ?
                ORDER BY message_index
                """,
        (rs, rowNum) -> toMessage(rs.getString("role"), rs.getString("content")),
        memoryId.toString());
  }

  @Override
  public void updateMessages(Object memoryId, List<ChatMessage> messages) {
    // to keep it simple for demo purpose, just delete everything and reinsert
    jdbc.update("DELETE FROM chat_memory WHERE memory_id = ?", memoryId.toString());

    for (int i = 0; i < messages.size(); i++) {
      ChatMessage m = messages.get(i);

      jdbc.update(
          """
                    INSERT INTO chat_memory(memory_id, message_index, role, content)
                    VALUES (?, ?, ?, ?)
                    """,
          memoryId.toString(),
          i,
          roleOf(m),
          textOf(m));
    }
  }

  @Override
  public void deleteMessages(Object memoryId) {
    jdbc.update("DELETE FROM chat_memory WHERE memory_id = ?", memoryId.toString());
  }

  private ChatMessage toMessage(String role, String content) {
    return switch (role) {
      case "user" -> UserMessage.from(content);
      case "assistant" -> AiMessage.from(content);
      case "system" -> SystemMessage.from(content);
      default -> throw new IllegalArgumentException("Unknown role " + role);
    };
  }

  private String roleOf(ChatMessage m) {
    if (m instanceof UserMessage) {
      return "user";
    }
    if (m instanceof AiMessage) {
      return "assistant";
    }
    if (m instanceof SystemMessage) {
      return "system";
    }
    throw new IllegalArgumentException("Unknown message type " + m);
  }

  private String textOf(ChatMessage m) {
    if (m instanceof UserMessage u) {
      return u.singleText();
    }
    if (m instanceof AiMessage a) {
      return a.text();
    }
    if (m instanceof SystemMessage s) {
      return s.text();
    }
    throw new IllegalArgumentException("Unknown message type " + m);
  }
}
