package com.milfist.reactiveconsumer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Basic {
  private String name;
  private String message;

  public static Basic create(Greet g, Person p) {
    Basic b = new Basic();
    b.setMessage(g.getMessage());
    b.setName(p.getName());
    return b;
  }

}
