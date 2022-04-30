package egger.software.graphqlexamples;

import egger.software.graphqlexamples.entity.Passenger;
import egger.software.graphqlexamples.repository.PassengersRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Map;

public class AddPassengerMutation implements DataFetcher<Passenger> {

    private final PassengersRepository repository;

    public AddPassengerMutation(PassengersRepository repository) {
        this.repository = repository;
    }

    @Override
    public Passenger get(DataFetchingEnvironment environment) {
        Map<String, String> passengerInput = environment.getArgument("passengerInput");
        Long flightId = Long.parseLong(environment.getArgument("flightId"));
        return repository.add(new Passenger(
                null,
                passengerInput.get("name"),
                flightId
                ));
    }
}
