package com.demo.drone.infrastructure.messaging;

import com.demo.drone.domain.DomainEvent;
import com.demo.drone.domain.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudStreamDomainEventPublisher implements DomainEventPublisher {

  private final String BINDING_NAME = "droneEventSupplier-out-0";

  private final StreamBridge streamBridge;

  @Override
  public void publish(DomainEvent event) {
    String partitionKey =
        event.getEntityId();

    Message<DomainEvent> eventMessage = MessageBuilder.withPayload(event)
            .setHeader(KafkaHeaders.MESSAGE_KEY, partitionKey)
            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build();
    streamBridge.send(BINDING_NAME, eventMessage);

    log.info(
        "Message was published with headers {} and payload {}",
        eventMessage.getHeaders(),
        eventMessage.getPayload());
  }
}
