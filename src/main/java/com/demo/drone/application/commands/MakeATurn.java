package com.demo.drone.application.commands;

import com.demo.drone.domain.DroneName;
import com.demo.drone.domain.TurnType;
import com.demo.drone.infrastructure.http.TurnRequest;
import lombok.Value;

import java.time.ZonedDateTime;

@Value(staticConstructor = "of")
public class MakeATurn {
  DroneName droneName;
  TurnType turnType;
  ZonedDateTime madeATurnAt;

  public static MakeATurn from(TurnRequest request) {
    return MakeATurn.of(
        DroneName.of(request.getDroneId()),
        TurnType.valueFrom(request.getTurnType().toString()),
        ZonedDateTime.now());
  }
}
