package com.demo.drone.domain.events;

import com.demo.drone.domain.DomainEvent;
import com.demo.drone.domain.DroneName;
import com.demo.drone.domain.Position;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value(staticConstructor = "of")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DroneMoved extends DomainEvent {
  DroneName droneId;
  Position oldPosition;
  Position newPosition;

  @Override
  @JsonIgnore
  protected String getEventName() {
    return "com.demo.drone.DroneMoved";
  }

  @Override
  @JsonIgnore
  public String getEntityId() {
    return droneId.getName();
  }
}
