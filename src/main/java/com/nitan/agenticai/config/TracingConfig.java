package com.nitan.agenticai.config;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracingConfig {

  @Bean
  Tracer tracer() {
    return GlobalOpenTelemetry.getTracer("com.nitan.agenticai");
  }
}
