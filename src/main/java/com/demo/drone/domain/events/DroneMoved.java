package com.demo.drone.domain.events;

import com.demo.drone.domain.DroneName;
import com.demo.drone.domain.Position;
import lombok.Value;

@Value(staticConstructor = "of")
public class DroneMoved {
  DroneName droneId;
  Position oldPosition;
  Position newPosition;
}
