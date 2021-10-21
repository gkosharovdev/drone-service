package com.demo.drone.domain;

import java.util.Map;
import java.util.stream.Stream;

public enum Direction {
  NORTH("North"),
  SOUTH("South"),
  EAST("East"),
  WEST("West");

  private final String current;
  private static final Map<Direction, Map<TurnType, Direction>> adjacentDirectionsByTurnType =
      Map.of(
          NORTH, Map.of(TurnType.LEFT, WEST, TurnType.RIGHT, EAST),
          WEST, Map.of(TurnType.LEFT, SOUTH, TurnType.RIGHT, NORTH),
          EAST, Map.of(TurnType.LEFT, NORTH, TurnType.RIGHT, SOUTH),
          SOUTH, Map.of(TurnType.LEFT, EAST, TurnType.RIGHT, WEST));

  Direction(String current) {
    this.current = current;
  }

  public static Direction valueFrom(String direction) {
    return Stream.of(Direction.values())
        .filter(unit -> unit.name().equalsIgnoreCase(direction))
        .findFirst()
        .orElse(NORTH);
  }

  public boolean isAdjacent(Direction direction) {
    return Direction.adjacentDirectionsByTurnType.get(valueFrom(current)).containsValue(direction);
  }

  public Direction getDirectionAfterTurn(TurnType turnType) {
    return Direction.adjacentDirectionsByTurnType.get(valueFrom(current)).get(turnType);
  }
}
