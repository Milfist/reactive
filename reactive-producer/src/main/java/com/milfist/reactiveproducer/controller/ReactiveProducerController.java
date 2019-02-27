package com.milfist.reactiveproducer.controller;

import com.milfist.reactiveproducer.domain.Greet;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/producer")
public class ReactiveProducerController {

  /**
   * Generamos un flujo de saludos, del que tomamos 5 elementos con el momento actual.
   * Retrasamos cada elemento 1 segundo.
   * @return Flux<Greet>
   */
  @GetMapping("/flux")
  public Flux<Greet> greetingPublisher() {
    return Flux
        .<Greet>generate(sink -> sink.next(new Greet("Hello " + Instant.now().toString())))
        .take(5)
        .delayElements(Duration.ofSeconds(1));
  }

  /**
   * Crea un Publisher Mono de tipo Greet. Con el consumer en la lambda, hacemos uso del método success y devolvemos un
   * nuevo Greet con un mensaje.
   * Por último retenemos el resultado por n segundos.
   * @return Un Publisher Mono para ser consumido.
   */
  @GetMapping("/mono")
  public Mono<Greet> mono() {
    return Mono
        .<Greet>create(sink -> sink.success(new Greet("Hola!! ")))
        .delayElement(Duration.ofSeconds(1));
  }

  @GetMapping(value = "/publisher/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Greet> sseGreetings() {
    return Flux
        .<Greet>generate(sink -> sink.next(new Greet("Hello @" + Instant.now().toString())))
        .take(5)
        .delayElements(Duration.ofSeconds(1));
  }

  @GetMapping(value = "/flux/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Greet> events() {
    Flux<Greet> greetingFlux = Flux.fromStream(Stream.generate(() -> new Greet("Hello @" + Instant.now().toString())));
    Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
    return Flux.zip(greetingFlux, durationFlux).map(Tuple2::getT1);
  }

  /**
   * Simulación de una generación de PDF que tarda bastante tiempo y neceista ser asincrona
   * @return Mono<Boolean>
   */
  @GetMapping(value = "/generate")
  public Mono<Boolean> generatePDF() {
    return Mono
        .<Boolean>create(sink -> sink.success(Boolean.TRUE))
        .delayElement(Duration.ofSeconds(5));
  }

}
