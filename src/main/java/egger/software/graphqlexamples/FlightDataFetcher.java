package egger.software.graphqlexamples;

import egger.software.graphqlexamples.entity.Flight;
import egger.software.graphqlexamples.repository.FlightsRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.Objects;

public class FlightDataFetcher implements DataFetcher<Flight> {

    private final FlightsRepository repository;

    public FlightDataFetcher(FlightsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flight get(DataFetchingEnvironment environment) {
        String number = environment.getArgument("number");
        List<Flight> flights = repository.find(flight -> Objects.equals(flight.getNumber(), number));
        if (flights.size() == 0)
            throw new FlightException("Flight with number: " + number + " not found");
        if (flights.size() != 1)
            throw new FlightException("More than one flight found with number: " + number);
        return flights.get(0);
    }

}
