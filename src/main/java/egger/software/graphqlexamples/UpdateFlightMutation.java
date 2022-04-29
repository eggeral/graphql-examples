package egger.software.graphqlexamples;

import egger.software.graphqlexamples.entity.Flight;
import egger.software.graphqlexamples.repository.FlightsRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Map;

public class UpdateFlightMutation implements DataFetcher<Flight> {

    private final FlightsRepository repository;

    public UpdateFlightMutation(FlightsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flight get(DataFetchingEnvironment environment) {
        Map<String, String> flightInputMap = environment.getArgument("flightInput");
        Long id = Long.parseLong(environment.getArgument("id"));
        return repository.update(new Flight(
                id,
                flightInputMap.get("number"),
                flightInputMap.get("from"),
                flightInputMap.get("to")
                ));
    }
}
