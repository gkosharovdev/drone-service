package com.demo.drone.domain;

import com.demo.drone.application.commands.DeployDrone;
import com.demo.drone.application.commands.MakeATurn;
import com.demo.drone.domain.exceptions.IllegalChangeOfDirectionException;
import com.demo.drone.domain.exceptions.PositionOutOfFlyingAreaException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DroneTest {

  private static final FlyingArea FLYING_AREA = FlyingArea.of(10, 10);
  private static final DroneName DRONE_NAME = DroneName.of("TEST_DRONE_1");
  private static final Direction NORTH = Direction.NORTH;

  @Test
  void should_be_able_to_deploy_drone() {
    var startingPosition = Position.of(5, 5);
    var drone = createATestDrone(NORTH, startingPosition);
    assertEquals(DRONE_NAME, drone.getName());
    assertEquals(FLYING_AREA, drone.getCurrentFlyingArea());
    assertEquals(NORTH, drone.getCurrentDirection());
    assertEquals(startingPosition, drone.getCurrentPosition());
  }

  @Test
  void should_be_able_to_turn_to_adjacent_directions_only() {
    var startingPosition = Position.of(5, 5);
    var drone = createATestDrone(NORTH, startingPosition);
    var turn = TurnType.RIGHT;
    var newDirection = Direction.EAST;
    var makeATurnCommand =
        MakeATurn.of(DRONE_NAME, turn, ZonedDateTime.parse("2021-10-21T11:01:00Z"));
    drone.turn(makeATurnCommand);
    assertEquals(newDirection, drone.getCurrentDirection());
  }

  @Test
  void should_be_able_to_go_northbound_respective_direction() {
    var startingPosition = Position.of(5, 5);
    var newPosition = Position.of(6, 5);
    var drone = createATestDrone(NORTH, startingPosition);
    drone.move();

    assertEquals(newPosition, drone.getCurrentPosition());
  }

  @Test
  void should_be_able_to_go_southbound_respective_direction() {
    var startingPosition = Position.of(5, 5);
    var newPosition = Position.of(4, 5);
    var drone = createATestDrone(Direction.SOUTH, startingPosition);
    drone.move();

    assertEquals(newPosition, drone.getCurrentPosition());
  }

  @Test
  void should_be_able_to_go_eastbound_respective_direction() {
    var startingPosition = Position.of(5, 5);
    var newPosition = Position.of(5, 6);
    var drone = createATestDrone(Direction.EAST, startingPosition);
    drone.move();

    assertEquals(newPosition, drone.getCurrentPosition());
  }

  @Test
  void should_be_able_to_go_westbound_respective_direction() {
    var startingPosition = Position.of(5, 5);
    var newPosition = Position.of(5, 4);
    var drone = createATestDrone(Direction.WEST, startingPosition);
    drone.move();

    assertEquals(newPosition, drone.getCurrentPosition());
  }

  @Test
  void should_throw_exception_when_going_outside_flying_area_northbound() {
    var startingPosition = Position.of(10, 5);
    var drone = createATestDrone(Direction.NORTH, startingPosition);
    assertThrows(PositionOutOfFlyingAreaException.class, drone::move);
  }

  @Test
  void should_throw_exception_when_going_outside_flying_area_eastbound() {
    var startingPosition = Position.of(5, 10);
    var drone = createATestDrone(Direction.EAST, startingPosition);
    assertThrows(PositionOutOfFlyingAreaException.class, drone::move);
  }

  @Test
  void should_throw_exception_when_going_outside_flying_area_westbound() {
    var startingPosition = Position.of(5, 0);
    var drone = createATestDrone(Direction.WEST, startingPosition);
    assertThrows(PositionOutOfFlyingAreaException.class, drone::move);
  }

  @Test
  void should_throw_exception_when_going_outside_flying_area_southbound() {
    var startingPosition = Position.of(0, 5);
    var drone = createATestDrone(Direction.SOUTH, startingPosition);
    assertThrows(PositionOutOfFlyingAreaException.class, drone::move);
  }

  private Drone createATestDrone(Direction direction, Position position) {

    var deployDrone =
        DeployDrone.of(
            DRONE_NAME,
            FLYING_AREA,
            position,
            direction,
            ZonedDateTime.parse("2021-10-21T11:00:00Z"));
    return Drone.fromCommand(deployDrone);
  }
}
