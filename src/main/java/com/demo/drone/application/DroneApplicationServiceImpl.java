package com.demo.drone.application;

import com.demo.drone.application.commands.DeployDrone;
import com.demo.drone.application.commands.MakeATurn;
import com.demo.drone.domain.Drone;
import com.demo.drone.domain.DroneName;
import com.demo.drone.domain.DroneRepository;
import com.demo.drone.infrastructure.http.Direction;
import com.demo.drone.infrastructure.http.DroneDto;
import com.demo.drone.infrastructure.http.MoveRequest;
import com.demo.drone.infrastructure.http.DeployDroneRequest;
import com.demo.drone.infrastructure.http.Position;
import com.demo.drone.infrastructure.http.TurnRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DroneApplicationServiceImpl implements DroneApplicationService {

  private final DroneRepository repository;

  @Override
  public DroneDto deployDrone(DeployDroneRequest request) {
    log.info("Deploying drone {}...", request.getDroneName());
    return repository
        .findByName(DroneName.of(request.getDroneName()))
        .map(this::convertDrone)
        .orElseGet(
            () -> {
              var drone = Drone.fromCommand(DeployDrone.from(request));
              repository.save(drone);
              log.info("Drone {} deployed", request.getDroneName());
              return convertDrone(drone);
            });
  }

  @Override
  public Optional<DroneDto> findByName(String name) {
    return repository.findByName(DroneName.of(name)).map(this::convertDrone);
  }

  @Override
  public Optional<DroneDto> turn(TurnRequest request) {
    log.info("Turning drone {}...", request.getDroneId());
    return repository
            .findByName(DroneName.of(request.getDroneId()))
            .map(
                    drone -> {
                        var command = MakeATurn.from(request);
                        drone.turn(command);
                        repository.save(drone);
                        log.info("Drone turn saved");
                        return convertDrone(drone);
                    });
  }

  @Override
  public Optional<DroneDto> move(MoveRequest request) {
    log.info("Moving drone {}...", request.getDroneId());
    return repository
        .findByName(DroneName.of(request.getDroneId()))
        .map(
            drone -> {
              drone.move();
              repository.save(drone);
              log.info("Drone move saved");
              return convertDrone(drone);
            });
  }

  private DroneDto convertDrone(Drone drone) {
    return DroneDto.of(
        drone.getName().getName(),
        Position.of(
            drone.getCurrentPosition().getNorthbound(), drone.getCurrentPosition().getNorthbound()),
        Direction.valueOf(drone.getCurrentDirection().name()));
  }
}
