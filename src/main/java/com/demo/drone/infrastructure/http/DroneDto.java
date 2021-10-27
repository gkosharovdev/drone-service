package com.demo.drone.infrastructure.http;

import lombok.Value;

@Value(staticConstructor = "of")
public class DroneDto {
    String name;
    Position position;
    Direction direction;
}
