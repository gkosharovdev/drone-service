package com.demo.drone.infrastructure.http;

public class DroneNotFoundException extends RuntimeException{
    public DroneNotFoundException(String name) {
        super(String.format("Drone %s not found", name));
    }
}
