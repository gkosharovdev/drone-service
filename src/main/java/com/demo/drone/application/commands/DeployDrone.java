package com.demo.drone.application.commands;

import com.demo.drone.domain.Direction;
import com.demo.drone.domain.DroneName;
import com.demo.drone.domain.FlyingArea;
import com.demo.drone.domain.Position;
import com.demo.drone.infrastructure.http.DeployDroneRequest;
import lombok.Value;

import java.time.ZonedDateTime;

@Value(staticConstructor = "of")
public class DeployDrone {
  DroneName name;
  FlyingArea flyingArea;
  Position position;
  Direction direction;
  ZonedDateTime deployAt;

  public static DeployDrone from(DeployDroneRequest request) {
    var flyingAreaId = FlyingArea.of(request.getFlyingAreaLength(), request.getFlyingAreaWidth());
    var name = DroneName.of(request.getDroneName());
    var position =
        Position.of(request.getDeploymentPosition().getX(), request.getDeploymentPosition().getY());
    var direction = Direction.valueFrom(request.getDirection());
    return DeployDrone.of(name, flyingAreaId, position, direction, ZonedDateTime.now());
  }
}
