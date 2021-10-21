package com.demo.drone.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class FlyingArea {
  int northAxisLength;
  int eastAxisLength;

  public boolean isPositionWithinArea(Position position) {
    return position.getEastbound() <= eastAxisLength
        && position.getEastbound() >= 0
        && position.getNorthbound() <= northAxisLength
        && position.getNorthbound() >= 0;
  }
}
