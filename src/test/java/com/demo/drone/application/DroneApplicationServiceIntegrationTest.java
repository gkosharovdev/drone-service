package com.demo.drone.application;

import com.demo.drone.domain.DroneName;
import com.demo.drone.domain.DroneRepository;
import com.demo.drone.infrastructure.http.DeployDroneRequest;
import com.demo.drone.infrastructure.http.Direction;
import com.demo.drone.infrastructure.http.Position;
import com.demo.drone.infrastructure.http.TurnRequest;
import com.demo.drone.infrastructure.http.TurnType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({TestChannelBinderConfiguration.class, DroneApplicationServiceImpl.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class DroneApplicationServiceIntegrationTest {

  private static final String DRONE_NAME = "TEST_DRONE";
  private static final Direction DRONE_DEPLOY_DIRECTION = Direction.NORTH;
  private static final Position DRONE_DEPLOY_POSITION = Position.of(5, 5);

  @Autowired private DroneApplicationService droneApplicationService;
  @Autowired private DroneRepository droneRepository;

  @BeforeEach
  void setup() {
    droneRepository.deleteAll();
  }

  @Test
  void should_deploy_and_persist_drone_correctly() {
    var request =
        DeployDroneRequest.of(
            DRONE_NAME, 10, 10, DRONE_DEPLOY_DIRECTION.toString(), DRONE_DEPLOY_POSITION);
    droneApplicationService.deployDrone(request);
    var result = droneRepository.findByName(DroneName.of(DRONE_NAME));
    assertThat(result).isNotEmpty();
    assertThat(result)
        .hasValueSatisfying(
            actual -> {
              assertThat(actual.getName()).isEqualTo(DroneName.of(DRONE_NAME));
              assertThat(actual.getCurrentDirection())
                  .isEqualTo(com.demo.drone.domain.Direction.NORTH);
            });
  }

  @Test
  void should_be_able_to_make_turn_and_persist() {
    var deployRequest =
        DeployDroneRequest.of(
            DRONE_NAME, 10, 10, DRONE_DEPLOY_DIRECTION.toString(), DRONE_DEPLOY_POSITION);
    var turnRequest = TurnRequest.of(DRONE_NAME, TurnType.RIGHT, ZonedDateTime.parse("2021-10-21T11:00:00Z"));
    droneApplicationService.deployDrone(deployRequest);
    droneApplicationService.turn(turnRequest);
    var result = droneRepository.findByName(DroneName.of(DRONE_NAME));
    assertThat(result).isNotEmpty();
    assertThat(result)
            .hasValueSatisfying(
                    actual -> {
                      assertThat(actual.getName()).isEqualTo(DroneName.of(DRONE_NAME));
                      assertThat(actual.getCurrentDirection())
                              .isEqualTo(com.demo.drone.domain.Direction.EAST);
                    });
  }
}
