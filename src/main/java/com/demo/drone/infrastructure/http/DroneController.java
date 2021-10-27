package com.demo.drone.infrastructure.http;

import com.demo.drone.application.DroneApplicationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;
import java.util.function.Predicate;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DroneController {

  private final DroneApplicationService droneApplicationService;

  @PostMapping("/v1/drones")
  public ResponseEntity<?> registerDrone(@Valid @RequestBody DeployDroneRequest request) {
    return wrapResult(droneApplicationService.deployDrone(request));
  }

  @PostMapping("/v1/drones/{name}/turn")
  public ResponseEntity<?> turn(@Valid @RequestBody TurnRequest request) {

    var drone = droneApplicationService.turn(request);
    drone.orElseThrow(() -> new DroneNotFoundException(request.getDroneId()));
    return wrapResult(drone);
  }

  @PostMapping("/v1/drones/{name}/move")
  public ResponseEntity<?> move(@Valid @RequestBody MoveRequest request) {
    var drone = droneApplicationService.move(request);
    drone.orElseThrow(() -> new DroneNotFoundException(request.getDroneId()));
    return wrapResult(drone);
  }

  @GetMapping("/v1/drones/{name}")
  public ResponseEntity<?> getDrone(@PathVariable String name) {

    return wrapResult(droneApplicationService.findByName(name));
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
