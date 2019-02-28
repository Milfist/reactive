package com.milfist.reactiveproducer.repository;

import com.milfist.reactiveproducer.domain.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonRepository extends ReactiveCrudRepository<Person, String> {
}
