package com.demo.drone.domain.events;

import com.demo.drone.domain.Direction;
import com.demo.drone.domain.DroneName;
import lombok.Value;

import java.time.ZonedDateTime;

@Value(staticConstructor = "of")
public class DroneTurned {
    DroneName droneId;
    Direction oldDirection;
    Direction newDirection;
    ZonedDateTime turnedAt;
}
