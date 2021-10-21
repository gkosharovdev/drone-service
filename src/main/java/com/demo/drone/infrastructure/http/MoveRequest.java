package com.demo.drone.infrastructure.http;

import lombok.Value;

import java.time.ZonedDateTime;

@Value(staticConstructor = "of")
public class MoveRequest {
    String droneId;
    ZonedDateTime requestedAt;
}
