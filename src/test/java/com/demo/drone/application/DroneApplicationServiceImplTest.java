package com.demo.drone.application;

import com.demo.drone.application.commands.DeployDrone;
import com.demo.drone.domain.Drone;
import com.demo.drone.domain.DroneName;
import com.demo.drone.domain.DroneRepository;
import com.demo.drone.domain.FlyingArea;
import com.demo.drone.infrastructure.http.DeployDroneRequest;
import com.demo.drone.infrastructure.http.Direction;
import com.demo.drone.infrastructure.http.MoveRequest;
import com.demo.drone.infrastructure.http.Position;
import com.demo.drone.infrastructure.http.TurnRequest;
import com.demo.drone.infrastructure.http.TurnType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DroneApplicationServiceImplTest {

  private static final String DRONE_NAME = "TEST_DRONE";
  private static final Direction DRONE_DEPLOY_DIRECTION = Direction.NORTH;
  private static final Position DRONE_DEPLOY_POSITION = Position.of(5, 5);
  private static final FlyingArea FLYING_AREA = FlyingArea.of(10, 10);

  @InjectMocks private DroneApplicationServiceImpl droneApplicationService;

  @Mock private DroneRepository droneRepository;

  @Test
  void should_be_able_to_deploy_drone() {
    var drone = createATestDrone();
    when(droneRepository.findByName(any())).thenReturn(Optional.empty());
    var request =
        DeployDroneRequest.of(
            DRONE_NAME, 10, 10, DRONE_DEPLOY_DIRECTION.toString(), DRONE_DEPLOY_POSITION);
    droneApplicationService.deployDrone(request);

    ArgumentCaptor<Drone> argument = ArgumentCaptor.forClass(Drone.class);
    verify(droneRepository).save(argument.capture());
  }

  @Test
  void should_be_able_to_command_a_turn() {

    var drone = createATestDrone();
    when(droneRepository.findByName(any())).thenReturn(Optional.of(drone));
    var request =
        TurnRequest.of(DRONE_NAME, TurnType.RIGHT, ZonedDateTime.parse("2021-10-21T11:00:01Z"));

    ArgumentCaptor<Drone> argument = ArgumentCaptor.forClass(Drone.class);
    droneApplicationService.turn(request);
    verify(droneRepository).save(argument.capture());
    assertThat(argument.getValue().getCurrentDirection())
        .isEqualTo(com.demo.drone.domain.Direction.EAST);
  }

  @Test
  void should_be_able_to_command_a_move() {
    var drone = createATestDrone();
    when(droneRepository.findByName(any())).thenReturn(Optional.of(drone));
    var request = MoveRequest.of(DRONE_NAME, ZonedDateTime.parse("2021-10-21T11:00:01Z"));
    ArgumentCaptor<Drone> argument = ArgumentCaptor.forClass(Drone.class);
    droneApplicationService.move(request);
    verify(droneRepository).save(argument.capture());
    assertThat(argument.getValue().getCurrentPosition())
        .isEqualTo(com.demo.drone.domain.Position.of(6, 5));
  }

  private Drone createATestDrone() {
    var deployDrone =
        DeployDrone.of(
            DroneName.of(DRONE_NAME),
            FLYING_AREA,
            com.demo.drone.domain.Position.of(
                DRONE_DEPLOY_POSITION.getX(), DRONE_DEPLOY_POSITION.getY()),
            com.demo.drone.domain.Direction.NORTH,
            ZonedDateTime.parse("2021-10-21T11:00:00Z"));
    return Drone.fromCommand(deployDrone);
  }
}
