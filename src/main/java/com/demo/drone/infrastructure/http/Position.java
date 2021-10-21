package com.demo.drone.infrastructure.http;

import lombok.Value;

@Value(staticConstructor = "of")
public class Position {
    int x;
    int y;
}
