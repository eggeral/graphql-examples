package egger.software.graphqlexamples;

import egger.software.graphqlexamples.entity.Flight;
import egger.software.graphqlexamples.repository.FlightsRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.Objects;

public class FlightsDataFetcher implements DataFetcher<List<Flight>> {

    private final FlightsRepository repository;

    public FlightsDataFetcher(FlightsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Flight> get(DataFetchingEnvironment environment) {
        String from = environment.getArgument("from");
        if (from != null)
            return repository.find(flight -> Objects.equals(flight.getFrom(), from));
        return repository.findAll();
    }
}
