package egger.software.graphqlexamples;

import egger.software.graphqlexamples.entity.Flight;
import egger.software.graphqlexamples.entity.Passenger;
import egger.software.graphqlexamples.repository.FlightsRepository;
import egger.software.graphqlexamples.repository.PassengersRepository;
import graphql.kickstart.servlet.GraphQLConfiguration;
import graphql.kickstart.servlet.GraphQLHttpServlet;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GraphQLServlet extends GraphQLHttpServlet {

    private final FlightsRepository flightsRepository;
    private final PassengersRepository passengersRepository;

    public GraphQLServlet() {
        this.flightsRepository = new FlightsRepository(Arrays.asList(
                new Flight(1L, "OS2001", "VIE", "DRS"),
                new Flight(2L, "LH1234", "GRZ", "BER"),
                new Flight(3L, "KM6712", "MUN", "FRA")
        ));
        this.passengersRepository = new PassengersRepository(Arrays.asList(
                new Passenger(1L, "Max Muster", 1L),
                new Passenger(2L, "Franz Hofer", 1L),
                new Passenger(3L, "John Doe", 2L)
        ));
    }

    @Override
    protected GraphQLConfiguration getConfiguration() {
        return GraphQLConfiguration.with(createSchema()).build();
    }

    private GraphQLSchema createSchema() {
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry =
                schemaParser.parse(GraphQLServlet.class.getResourceAsStream("/schema.graphql"));

        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("FlightsQuery", builder ->
                        builder.dataFetcher("flights", new FlightsDataFetcher(flightsRepository))
                )
                .type("Flight", builder ->
                        builder.dataFetcher("passengers", new PassengersDataFetcher(passengersRepository))
                )
                .type("FlightsMutation", builder ->
                        builder.dataFetcher("createFlight", new CreateFlightMutation(flightsRepository))
                )
                .type("FlightsMutation", builder ->
                        builder.dataFetcher("updateFlight", new UpdateFlightMutation(flightsRepository))
                )
                .type("FlightsMutation", builder ->
                        builder.dataFetcher("addPassenger", new AddPassengerMutation(passengersRepository))
                )
                .type("FlightsMutation", builder ->
                        builder.dataFetcher("flight", new FlightDataFetcher(flightsRepository))
                )
                .type("FlightMutation", builder ->
                        builder.dataFetcher("addPassenger", new AddPassengerToFlightMutation(passengersRepository))
                )
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }

}