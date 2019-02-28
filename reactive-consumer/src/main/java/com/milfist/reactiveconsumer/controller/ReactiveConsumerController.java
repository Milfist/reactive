package com.milfist.reactiveconsumer.controller;

import com.milfist.reactiveconsumer.domain.Basic;
import com.milfist.reactiveconsumer.domain.Greet;
import com.milfist.reactiveconsumer.domain.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/consumer")
public class ReactiveConsumerController {

  private WebClient client;

  @GetMapping("/flux")
  public Mono<ResponseEntity> getFlux() {
    callFluxConsumer();
    return Mono
        .create(sink -> sink.success(new ResponseEntity<>(HttpStatus.OK)));
  }

  @GetMapping("/mono")
  public Mono<Greet> getMono() {
    return callMonoConsumer();
  }

  @GetMapping("/generate")
  public Mono<ResponseEntity> generatePDF() {
    callGenerate();
    return Mono
        .create(sink -> sink.success(new ResponseEntity<>(HttpStatus.ACCEPTED)));
  }

  @GetMapping("/merge")
  public Flux<Basic> getMergeObjectByTwoCalls() {

    Flux<Greet> greets = client.get()
        .uri("/greets")
        .retrieve()
        .bodyToFlux(Greet.class);

    Flux<Person> persons = client.get()
        .uri("/persons")
        .retrieve()
        .bodyToFlux(Person.class);


    Flux<Basic> basic = greets.
        flatMap(greet -> persons.
            map(person -> Basic.create(greet, person))
        );

    return basic;
  }

  private void callFluxConsumer() {
    Instant start = Instant.now();

    client.get()
          .uri("/producer/flux")
          .retrieve()
          .bodyToFlux(Greet.class)
          .subscribe(greet -> log.info("------------> " + greet.getMessage()),
              err -> log.error("pues eso...error"),
              () -> logTime(start, "subscribe"));

    logTime(start, "function");
  }

  private Mono<Greet> callMonoConsumer() {
    Instant start = Instant.now();
    //TODO: Hay que hay algo raro...devuelve el objeto con los datos del producer, aunque me suscriba y los cambie...
    Mono<Greet> mono = client.get()
        .uri("/producer/mono")
        .retrieve()
        .bodyToMono(Greet.class);

    logTime(start, "function");

    mono
        .subscribe(greet -> greet.setMessage("menudo lio tengo...lo voy pillando"),
            err -> log.error("Error on Greet: " + err),
            () -> log.info("OK"));

    return mono;
  }

  private void callGenerate() {

    client.get()
        .uri("/producer/generate")
        .retrieve()
        .bodyToMono(Boolean.class)
        .subscribe(response -> {
              if(response) {
                log.info("Se ha generado correctamente, podría realizar operaciones extra de forma asíncrona...");
              } else {
                log.info("No se ha producido ningún error, pero no se ha generado por algo...");
              }
            },
            err -> log.error("Error creando PDF: " + err)
        );

  }

  private static void logTime(Instant start, String when) {
    log.debug("Elapsed time: " + when + " " + Duration.between(start, Instant.now()).toMillis() + "ms");
  }

}
