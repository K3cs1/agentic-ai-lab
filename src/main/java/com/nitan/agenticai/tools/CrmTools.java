package com.nitan.agenticai.tools;

import com.nitan.agenticai.domain.Address;
import com.nitan.agenticai.domain.Client;
import com.nitan.agenticai.domain.ClientType;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CrmTools {

  @Tool("Get Client by name.")
  public Client getClientByName(String name) {
    log.info("Searching in the CRM for client with name: {} ", name);
    if ("Acme Corporation".equals(name)) {
      return new Client(
          1L,
          name,
          Address.builder()
              .city("New York")
              .street("5th Avenue")
              .state("NY")
              .country("USA")
              .zipCode("10001")
              .build());
    }

    if ("Globex Corporation".equals(name)) {
      return new Client(
          2L,
          name,
          Address.builder()
              .city("Bucharest")
              .street("Victoriei Square")
              .state("Bucharest")
              .country("Romania")
              .zipCode("200002")
              .build());
    }

    throw new IllegalArgumentException("Client not found: " + name);
  }

  @Tool(
      "Get client type using the client's id.To be use before calculating the discount for a client.")
  public ClientType getClientTypeById(Long clientId) {
    log.info("Searching for client type of the client: {}", clientId);
    if (clientId == 1L) {
      return ClientType.GOLD;
    }

    if (clientId == 2L) {
      return ClientType.BRONZE;
    }

    throw new IllegalArgumentException("Unknown client type: " + clientId);
  }
}
