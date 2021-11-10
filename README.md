# drone-service

Drone service controls a drone flying in a field. The field is divided on sectors 10x10.

The service exposes a REST API to control the drone and inspect the current state.

Available commands are:

- Deploy drone
- Turn left/right
- Move forward

Constraints:
- Drone can look only 4 directions (North, South , West, East)
- Drone can turn only to neighbouring directions (If at North can turn only to West or East)

Technical:

The service implementation is following the Hexagon architecture. It is also emitting domain events as kafka messages for each successful change of the state of the drone. Following the 'domain events-first' mindset (although this is NOT an Eventsourcing solution) the Drone aggregate is applying the already instantiated domain events in order to advance to the next valid state resepcting the invariant rules. The aggregate keeps track of the published events by extedning Spring's AbstractAggregateRoot. This could be the base work for implementing the Transactional outbox (https://microservices.io/patterns/data/transactional-outbox.html) for reliable event publication.
