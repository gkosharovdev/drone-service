package com.demo.drone.application;

import com.demo.drone.infrastructure.http.MoveRequest;
import com.demo.drone.infrastructure.http.DeployDroneRequest;
import com.demo.drone.infrastructure.http.TurnRequest;

public interface DroneApplicationService {
    void turn(TurnRequest request);

    void move(MoveRequest Request);

    void deployDrone(DeployDroneRequest request);
}
