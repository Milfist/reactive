package com.milfist.reactiveproducer.controller;

import com.milfist.reactiveproducer.domain.Greet;
import com.milfist.reactiveproducer.domain.Person;
import com.milfist.reactiveproducer.repository.GreetRepository;
import com.milfist.reactiveproducer.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

@RestController
@AllArgsConstructor
public class ReactiveProducerMongoController {

  private GreetRepository greetRepository;
  private PersonRepository personRepository;

  @GetMapping("/greets")
  public Flux<Greet> findAllGreets() {
    return greetRepository.findAll();
  }

  @GetMapping("/greets/{id}")
  public Mono<Greet> findGreetById(@PathVariable String id) {
    return greetRepository.findById(id);
  }

  @GetMapping("/persons")
  public Flux<Person> findAllPersons() {
    return personRepository.findAll();
  }

  @GetMapping("/persons/{id}")
  public Mono<Person> findPersonById(@PathVariable String id) {
    return personRepository.findById(id);
  }

}
