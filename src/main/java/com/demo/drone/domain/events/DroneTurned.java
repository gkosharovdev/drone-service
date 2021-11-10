package com.demo.drone.domain.events;

import com.demo.drone.domain.Direction;
import com.demo.drone.domain.DomainEvent;
import com.demo.drone.domain.DroneName;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = true)
@Value(staticConstructor = "of")
public class DroneTurned extends DomainEvent {
    DroneName droneId;
    Direction oldDirection;
    Direction newDirection;
    ZonedDateTime turnedAt;

    @Override
    protected String getEventName() {
        return "com.demo.drone.DroneTurned";
    }

    @Override
    public String getEntityId() {
    return droneId.getName();
    }
}
