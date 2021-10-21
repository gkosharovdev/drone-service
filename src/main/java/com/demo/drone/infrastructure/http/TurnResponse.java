package com.demo.drone.infrastructure.http;

import lombok.Value;

@Value
public class TurnResponse {
    String droneId;
    int x;
    int y;
    Direction direction;
}
