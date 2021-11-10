package com.demo.drone.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DomainEventProcessor {

  private final DomainEventPublisher domainEventPublisher;

  @EventListener
  public void onEvent(DomainEvent event) {
    log.debug("Publishing event {}...", event.getEventName());
    domainEventPublisher.publish(event);
  }
}
