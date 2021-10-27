package com.demo.drone.application;

import com.demo.drone.infrastructure.http.DroneDto;
import com.demo.drone.infrastructure.http.MoveRequest;
import com.demo.drone.infrastructure.http.DeployDroneRequest;
import com.demo.drone.infrastructure.http.TurnRequest;

import java.util.Optional;

public interface DroneApplicationService {
    Optional<DroneDto> turn(TurnRequest request);

    Optional<DroneDto> move(MoveRequest Request);

    DroneDto deployDrone(DeployDroneRequest request);

    Optional<DroneDto> findByName(String name);
}
