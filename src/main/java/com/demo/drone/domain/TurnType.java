package com.demo.drone.domain;

import java.util.stream.Stream;

public enum TurnType {
  LEFT,
  RIGHT;

  public static TurnType valueFrom(String turnType) {
    return Stream.of(TurnType.values())
        .filter(unit -> unit.name().equalsIgnoreCase(turnType))
        .findFirst()
        .orElse(RIGHT);
  }
}
