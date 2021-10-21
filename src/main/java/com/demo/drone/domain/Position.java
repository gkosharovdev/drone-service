package com.demo.drone.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Position {
  int northbound;
  int eastbound;

  public Position changeNorthbound(int toAdd) {
    return new Position(northbound + toAdd, eastbound);
  }

  public Position changeSouthbound(int toAdd) {
    return new Position(northbound, eastbound + toAdd);
  }

  public Position changeEastbound(int toRemove) {
    return new Position(northbound - toRemove, eastbound);
  }

  public Position changeWestbound(int toRemove) {
    return new Position(northbound, eastbound - toRemove);
  }
}
