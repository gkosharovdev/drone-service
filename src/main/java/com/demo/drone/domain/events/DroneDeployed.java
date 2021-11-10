package com.demo.drone.domain.events;

import com.demo.drone.domain.Direction;
import com.demo.drone.domain.DomainEvent;
import com.demo.drone.domain.DroneName;
import com.demo.drone.domain.FlyingArea;
import com.demo.drone.domain.Position;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = false)
@Value(staticConstructor = "of")
public class DroneDeployed extends DomainEvent {
  DroneName droneName;
  FlyingArea flyingArea;
  Position position;
  Direction direction;
  ZonedDateTime deployedAt;

  @Override
  protected String getEventName() {
    return "com.demo.drone.DroneDeployed";
  }

  @Override
  public String getEntityId() {
    return droneName.getName();
  }
}
