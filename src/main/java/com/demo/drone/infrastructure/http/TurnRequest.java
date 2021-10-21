package com.demo.drone.infrastructure.http;

import lombok.Value;

import java.time.ZonedDateTime;

@Value(staticConstructor = "of")
public class TurnRequest {
    String droneId;
    TurnType turnType;
    ZonedDateTime requestedAt;
}
