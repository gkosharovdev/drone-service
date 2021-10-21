package com.demo.drone.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DroneRepository extends MongoRepository<Drone, DroneName> {
    Optional<Drone> findByName(DroneName name);
}
