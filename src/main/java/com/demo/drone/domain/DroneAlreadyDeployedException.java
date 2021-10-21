package com.demo.drone.domain;

public class DroneAlreadyDeployedException extends RuntimeException {
  public DroneAlreadyDeployedException(String message) {
    super(String.format("Drone %s already deployed", message));
  }
}
