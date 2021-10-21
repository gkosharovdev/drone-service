package com.demo.drone.application;

import com.demo.drone.application.commands.DeployDrone;
import com.demo.drone.application.commands.MakeATurn;
import com.demo.drone.domain.Drone;
import com.demo.drone.domain.DroneAlreadyDeployedException;
import com.demo.drone.domain.DroneName;
import com.demo.drone.domain.DroneRepository;
import com.demo.drone.infrastructure.http.MoveRequest;
import com.demo.drone.infrastructure.http.DeployDroneRequest;
import com.demo.drone.infrastructure.http.TurnRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DroneApplicationServiceImpl implements DroneApplicationService {

  private final DroneRepository repository;

  @Override
  public void deployDrone(DeployDroneRequest request) {
    log.info("Deploying drone {}...", request.getDroneName());
    repository
        .findByName(DroneName.of(request.getDroneName()))
        .ifPresentOrElse(
            drone -> {
              log.info("Drone {} already deployed", drone.getName());
              throw new DroneAlreadyDeployedException(drone.getName().toString());
            },
            () -> {
              var drone = Drone.fromCommand(DeployDrone.from(request));
              repository.save(drone);
              log.info("Drone {} deployed", request.getDroneName());
            });
  }

  @Override
  public void turn(TurnRequest request) {
    log.info("Turning drone {}...", request.getDroneId());
    repository
        .findByName(DroneName.of(request.getDroneId()))
        .ifPresentOrElse(
            drone -> {
              var command = MakeATurn.from(request);
              drone.turn(command);
              repository.save(drone);
              log.info("Drone turn saved");
            },
            () -> log.info("Drone {} cannot turn at the moment", request.getDroneId()));
  }

  @Override
  public void move(MoveRequest request) {
    log.info("Moving drone {}...", request.getDroneId());
    repository
        .findByName(DroneName.of(request.getDroneId()))
        .ifPresentOrElse(
            drone -> {
              drone.move();
              repository.save(drone);
              log.info("Drone move saved");
            },
            () -> log.info("Drone {} cannot turn at the moment", request.getDroneId()));
  }
}
