package com.demo.drone.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DirectionTest {

  @Test
  void should_be_able_to_instantiate_from_string() {
    var direction = Direction.valueFrom("north");
    assertEquals(direction, Direction.NORTH);
  }

  @Test
  void should_be_able_to_instantiate_from_mixed_case_string() {
    var direction = Direction.valueFrom("nOrtH");
    assertEquals(direction, Direction.NORTH);
  }

  @Test
  void should_instantiate_north_on_invalid_direction() {
    var direction = Direction.valueFrom("ffff");
    assertEquals(direction, Direction.NORTH);
  }

  @Test
  void should_be_able_to_check_for_adjacent_directions() {
    var direction = Direction.NORTH;
    assertTrue(direction.isAdjacent(Direction.WEST));
  }

  @Test
  void should_be_able_to_check_for_non_adjacent_directions() {
    var direction = Direction.NORTH;
    assertFalse(direction.isAdjacent(Direction.SOUTH));
  }

  @Test
  void should_be_able_to_get_direction_after_turn() {
    var direction = Direction.NORTH;
    assertEquals(Direction.EAST, direction.getDirectionAfterTurn(TurnType.RIGHT));
  }
}
