# drone-service

Drone service controls a drone flying in a field. The field is divided on sectors 10x10.

The service exposes a REST API to control the drone and inspect the current state.

Available commands are:

- Turn left/right
- Move forward

Constraints:
- Drone can look only 4 directions (North, South , West, East)
- Drone can turn only to neighbouring directions (If at North can turn only to West or East)

Technical:

The service implementation is following the Hexagon architecture. It is also emitting domain events for each successful change of the state of the drone.