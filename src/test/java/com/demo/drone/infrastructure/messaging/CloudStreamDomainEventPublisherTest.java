package com.demo.drone.infrastructure.messaging;

import com.demo.drone.domain.DroneName;
import com.demo.drone.domain.Position;
import com.demo.drone.domain.events.DroneMoved;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Import({TestChannelBinderConfiguration.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CloudStreamDomainEventPublisherTest {

    @Autowired
    private CloudStreamDomainEventPublisher publisher;

    @Autowired
    private OutputDestination output;

    @Autowired
    private ObjectMapper mapper;


    @Test
    @Timeout(60)
    void should_publish_domain_event() throws JsonProcessingException {
        var event = DroneMoved.of(DroneName.of("TEST-DRONE"), Position.of(5,5), Position.of(5,6));
        publisher.publish(event);
        Message<byte[]> outputMessage = output.receive(200, "demo.events.drone");
        var payload = mapper.readValue(new String(outputMessage.getPayload()), DroneMoved.class);
        assertThat(payload.getDroneId().getName()).isEqualTo("TEST-DRONE");
    }
}