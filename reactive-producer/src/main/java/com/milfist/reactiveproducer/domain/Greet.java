package com.milfist.reactiveproducer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "greets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Greet {
  @Id
  private String id;
  private String message;

  public static Greet create(String message) {
    Greet greet = new Greet();
    greet.setMessage(message);
    return greet;
  }
}
