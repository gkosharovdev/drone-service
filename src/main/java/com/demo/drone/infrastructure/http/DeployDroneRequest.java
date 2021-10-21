package com.demo.drone.infrastructure.http;

import lombok.Value;

@Value(staticConstructor = "of")
public class DeployDroneRequest {
  String droneName;
  int flyingAreaLength;
  int flyingAreaWidth;
  String direction;
  Position deploymentPosition;
}
