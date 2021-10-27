package com.demo.drone.domain.exceptions;

public class PositionOutOfFlyingAreaException extends RuntimeException {
    public PositionOutOfFlyingAreaException(String name) {
        super(String.format("Drone %s out of flying area", name));
    }
}
