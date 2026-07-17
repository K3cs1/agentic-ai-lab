package com.nitan.agenticai.tools;

import com.nitan.agenticai.util.AiTool;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WeatherTools implements AiTool {

  @Tool("Get the weather forecast for a given city")
  public String getWeather(String city) {
    log.info("Fetching weather for: {}", city);
    return "Tomorrow in " + city + ": sunny, 24°C, light breeze.";
  }
}
