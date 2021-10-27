package com.demo.drone.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DroneRepository extends MongoRepository<Drone, DroneName> {
    Optional<Drone> findByName(DroneName name);
}
