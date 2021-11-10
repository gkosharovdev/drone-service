package com.demo.drone.domain;

public interface DomainEventPublisher {

  void publish(DomainEvent event);
}
