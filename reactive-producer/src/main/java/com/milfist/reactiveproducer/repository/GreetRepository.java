package com.milfist.reactiveproducer.repository;

import com.milfist.reactiveproducer.domain.Greet;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface GreetRepository extends ReactiveCrudRepository<Greet, String> {
}
