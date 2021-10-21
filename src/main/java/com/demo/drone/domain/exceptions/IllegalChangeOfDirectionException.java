package com.demo.drone.domain.exceptions;

import com.demo.drone.domain.Direction;

public class IllegalChangeOfDirectionException extends RuntimeException {
  public IllegalChangeOfDirectionException(Direction direction) {
    super(String.format("Illegal direction %s. Can turn to adjacent directions only.", direction));
  }
}
