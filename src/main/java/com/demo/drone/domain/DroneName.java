package com.demo.drone.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class DroneName {
    String name;
}
