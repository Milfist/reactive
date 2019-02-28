package com.milfist.reactiveproducer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "persons")
@Data
@AllArgsConstructor
public class Person {
  @Id
  private String id;
  private String name;
}
