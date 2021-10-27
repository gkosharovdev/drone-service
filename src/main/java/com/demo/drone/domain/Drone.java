package com.demo.drone.domain;

import com.demo.drone.application.commands.DeployDrone;
import com.demo.drone.application.commands.MakeATurn;
import com.demo.drone.domain.events.DroneDeployed;
import com.demo.drone.domain.events.DroneMoved;
import com.demo.drone.domain.events.DroneTurned;
import com.demo.drone.domain.exceptions.IllegalChangeOfDirectionException;
import com.demo.drone.domain.exceptions.PositionOutOfFlyingAreaException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Document(collection = "drones")
@Slf4j
@Getter
public class Drone extends AbstractAggregateRoot<Drone> {
  @Id private String id;
  private DroneName name;
  private FlyingArea currentFlyingArea;
  private Position currentPosition;
  private Direction currentDirection;
  private ZonedDateTime deployedAt;

  private Drone(
      DroneName name,
      FlyingArea currentFlyingArea,
      Position currentPosition,
      Direction currentDirection,
      ZonedDateTime deployedAt) {

    var event =
        DroneDeployed.of(name, currentFlyingArea, currentPosition, currentDirection, deployedAt);
    applyEvent(event);
    log.info(
        "Drone {} deployed on area of size {}x{}",
        name,
        currentFlyingArea.getNorthAxisLength(),
        currentFlyingArea.getEastAxisLength());
  }

  public static Drone fromCommand(DeployDrone command) {
    return new Drone(
        command.getName(),
        command.getFlyingArea(),
        command.getPosition(),
        command.getDirection(),
        command.getDeployAt());
  }

  public void turn(MakeATurn command) {
    var newDirection = calcNewDirection(command.getTurnType());
    assertAllowedDirection(newDirection);
    var event =
        DroneTurned.of(
            command.getDroneName(),
            this.getCurrentDirection(),
            newDirection,
            command.getMadeATurnAt());
    applyEvent(event);
    log.info(
        "Drone {} turned from {} to {}",
        event.getDroneId(),
        event.getOldDirection(),
        event.getNewDirection());
  }

  public void move() {

    var newPosition = calcNewPosition();
    assertPosition(newPosition);
    var event = DroneMoved.of(this.getName(), this.getCurrentPosition(), newPosition);
    applyEvent(event);
    log.info(
        "Drone {} moved from {} to {}",
        event.getDroneId(),
        event.getOldPosition(),
        event.getNewPosition());
  }

  private void applyEvent(DroneDeployed event) {
    this.name = event.getDroneName();
    this.currentFlyingArea = event.getFlyingArea();
    this.currentPosition = event.getPosition();
    this.currentDirection = event.getDirection();
    this.deployedAt = event.getDeployedAt();
    registerEvent(event);
  }

  private void applyEvent(DroneTurned event) {
    this.currentDirection = event.getNewDirection();
    registerEvent(event);
  }

  private void applyEvent(DroneMoved event) {
    this.currentPosition = event.getNewPosition();
    registerEvent(event);
  }

  private Direction calcNewDirection(TurnType turnType) {
    return this.getCurrentDirection().getDirectionAfterTurn(turnType);
  }

  private Position calcNewPosition() {
    switch (this.getCurrentDirection()) {
      case NORTH:
        return this.getCurrentPosition().changeNorthbound(1);
      case SOUTH:
        return this.getCurrentPosition().changeEastbound(1);
      case EAST:
        return this.getCurrentPosition().changeSouthbound(1);
      case WEST:
        return this.getCurrentPosition().changeWestbound(1);
      default:
        return this.getCurrentPosition();
    }
  }

  private void assertPosition(Position position) {
    if (!this.currentFlyingArea.isPositionWithinArea(position)) {
      throw new PositionOutOfFlyingAreaException(this.getName().getName());
    }
  }

  private void assertAllowedDirection(Direction direction) {
    var currentDirection = this.getCurrentDirection();
    if (!currentDirection.isAdjacent(direction)) {
      throw new IllegalChangeOfDirectionException(direction);
    }
  }
}
