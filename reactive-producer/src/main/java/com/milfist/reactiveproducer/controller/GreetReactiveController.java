package com.milfist.reactiveproducer.controller;

import com.milfist.reactiveproducer.domain.Greeting;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@RestController
@RequestMapping("/greetings")
public class GreetReactiveController {

  @GetMapping("/flux")
  public Publisher<Greeting> greetingPublisher() {
    return Flux
        .<Greeting>generate(sink -> sink.next(new Greeting("Hello " + Instant.now().toString())))
        .take(3);
  }

  @GetMapping(value = "/publisher/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Publisher<Greeting> sseGreetings() {
    return Flux
        .<Greeting>generate(sink -> sink.next(new Greeting("Hello @" + Instant.now().toString())))
        .take(5)
        .delayElements(Duration.ofSeconds(1));
  }

  @GetMapping(value = "/flux/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Greeting> events() {
    Flux<Greeting> greetingFlux = Flux.fromStream(Stream.generate(() -> new Greeting("Hello @" + Instant.now().toString())));
    Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
    return Flux.zip(greetingFlux, durationFlux).map(Tuple2::getT1);
  }

}