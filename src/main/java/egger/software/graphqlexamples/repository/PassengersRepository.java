package egger.software.graphqlexamples.repository;

import egger.software.graphqlexamples.entity.Passenger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PassengersRepository {
    private final List<Passenger> passengers = new ArrayList<>();

    public PassengersRepository(List<Passenger> initialPassengers) {
        passengers.addAll(initialPassengers.stream().map(Passenger::copy).collect(Collectors.toList()));
    }

    public List<Passenger> findAll() {
        return passengers.stream().map(Passenger::copy).collect(Collectors.toList());
    }

    public Passenger findById(Long id) {
        return find(passenger -> Objects.equals(passenger.getId(), id)).stream().findAny().orElse(null);
    }

    public List<Passenger> find(Predicate<Passenger> passengerPredicate) {
        return passengers.stream().filter(passengerPredicate).map(Passenger::copy).collect(Collectors.toList());
    }

    public Passenger add(Passenger newPassenger) {
        Long newId = passengers.stream().map(Passenger::getId).max(Long::compareTo).orElse(0L) + 1;
        Passenger passenger = newPassenger.copy(newId, null, null);
        passengers.add(passenger);
        return passenger;
    }

    public void delete(Long id) {
        passengers.removeIf(passenger -> Objects.equals(passenger.getId(), id));
    }

    public Passenger update(Passenger updatedPassenger) {
        delete(updatedPassenger.getId());
        Passenger passenger = updatedPassenger.copy(updatedPassenger.getId(), null, null);
        passengers.add(passenger);
        return passenger;
    }
}
