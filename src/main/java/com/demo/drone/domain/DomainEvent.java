package com.demo.drone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.net.URI;
import java.util.UUID;

@ToString
@Slf4j
public abstract class DomainEvent {

  @JsonIgnore @Getter private String id = UUID.randomUUID().toString();

  protected Object getData() {
    return this;
  }

  protected abstract String getEventName();

  public abstract String getEntityId();
}
