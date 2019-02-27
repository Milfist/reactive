package com.milfist.reactiveconsumer.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Data
public class WebClientConf {

  //TODO: Refactor

  @Value("${reactive-producer.server}")
  private String server;

  @Bean
  public WebClient webClient() {
    return WebClient.create(server);
  }
}
