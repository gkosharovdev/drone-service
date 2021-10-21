package com.demo.drone.domain.events;

import com.demo.drone.domain.Direction;
import com.demo.drone.domain.DroneName;
import com.demo.drone.domain.FlyingArea;
import com.demo.drone.domain.Position;
import lombok.Value;

import java.time.ZonedDateTime;

@Value(staticConstructor = "of")
public class DroneDeployed {
  DroneName droneName;
  FlyingArea flyingArea;
  Position position;
  Direction direction;
  ZonedDateTime deployedAt;
}
