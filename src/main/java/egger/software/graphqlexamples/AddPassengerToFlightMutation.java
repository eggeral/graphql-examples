package egger.software.graphqlexamples;

import egger.software.graphqlexamples.entity.Flight;
import egger.software.graphqlexamples.entity.Passenger;
import egger.software.graphqlexamples.repository.PassengersRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Map;

public class AddPassengerToFlightMutation implements DataFetcher<Passenger> {

    private final PassengersRepository repository;

    public AddPassengerToFlightMutation(PassengersRepository repository) {
        this.repository = repository;
    }

    @Override
    public Passenger get(DataFetchingEnvironment environment) {
        Map<String, String> passengerInput = environment.getArgument("passengerInput");
        Flight flight = environment.getSource();
        return repository.add(new Passenger(
                null,
                passengerInput.get("name"),
                flight.getId()
                ));
    }
}
