package com.demo.drone.domain.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DroneMovedTest {

    @Test
    void should_be_able_to_deserialize() throws Exception {
        //var event = DroneMoved.of(DroneName.of("TEST-DRONE"), Position.of(5,5), Position.of(5,6));
        var mapper = new ObjectMapper();
        var serializedContent = "{\n" +
                "  \"droneId\": {\n" +
                "    \"name\": \"TEST-DRONE\"\n" +
                "  },\n" +
                "  \"oldPosition\": {\n" +
                "    \"northbound\": 5,\n" +
                "    \"eastbound\": 5\n" +
                "  },\n" +
                "  \"newPosition\": {\n" +
                "    \"northbound\": 5,\n" +
                "    \"eastbound\": 6\n" +
                "  },\n" +
                "  \"entityId\": \"TEST-DRONE\"\n" +
                "}";
        var result = mapper.readValue(serializedContent, DroneMoved.class);
        assertThat(result.getDroneId().getName()).isEqualTo("TEST-DRONE");
    }
}