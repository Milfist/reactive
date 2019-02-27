package com.milfist.reactiveconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class ReactiveConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveConsumerApplication.class, args);
	}

}
