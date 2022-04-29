package egger.software.graphqlexamples;

import egger.software.graphqlexamples.entity.Flight;
import egger.software.graphqlexamples.entity.Passenger;
import egger.software.graphqlexamples.repository.PassengersRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.Objects;

public class PassengersDataFetcher implements DataFetcher<List<Passenger>> {

    private final PassengersRepository repository;

    public PassengersDataFetcher(PassengersRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Passenger> get(DataFetchingEnvironment environment) {
        Flight flight = environment.getSource();
        return repository.find(passenger -> Objects.equals(passenger.getFlightId(), flight.getId()));
    }
}
