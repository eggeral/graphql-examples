package egger.software.graphqlexamples;

import egger.software.graphqlexamples.entity.Flight;
import egger.software.graphqlexamples.repository.FlightsRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CreateFlightMutation implements DataFetcher<Flight> {

    private final FlightsRepository repository;

    public CreateFlightMutation(FlightsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flight get(DataFetchingEnvironment environment) {
        Map<String, String> flightInputMap = environment.getArgument("flightInput");
        return repository.add(new Flight(
                null,
                flightInputMap.get("number"),
                flightInputMap.get("from"),
                flightInputMap.get("to")
                ));
    }
}
