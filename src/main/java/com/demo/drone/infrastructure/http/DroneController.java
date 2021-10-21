package com.demo.drone.infrastructure.http;

import com.demo.drone.application.DroneApplicationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DroneController {

  private final DroneApplicationService droneApplicationService;

  @PostMapping("/v1/drones")
  void registerDrone(DeployDroneRequest request) {
    droneApplicationService.deployDrone(request);
  }

  @PostMapping("/v1/drone/{id}/turn")
  void turn(TurnRequest request) {
    droneApplicationService.turn(request);
  }

  @PostMapping
  void move(MoveRequest request) {
    droneApplicationService.move(request);
  }

  @NonNull
  private <T> ResponseEntity<T> wrapResult(T result) {
    return wrapResult(Objects::isNull, result);
  }

  @NonNull
  private <T> ResponseEntity<T> wrapResult(Predicate<T> assertResult, T result) {
    return assertResult.test(result)
        ? ResponseEntity.notFound().build()
        : ResponseEntity.ok(result);
  }
}
