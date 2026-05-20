package com.nitan.agenticai.repository;

import com.nitan.agenticai.dto.Prompt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PromptRepository {

  private final JdbcTemplate jdbc;

  public Optional<Prompt> findByKey(String promptKey) {
    String sql =
        """
            SELECT id, prompt_key, content
            FROM prompts
            WHERE prompt_key = ?  AND active = true
            LIMIT 1
            """;

    return jdbc.query(sql, this::mapRow, promptKey).stream().findFirst();
  }

  private Prompt mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new Prompt(rs.getLong("id"), rs.getString("prompt_key"), rs.getString("content"));
  }
}
