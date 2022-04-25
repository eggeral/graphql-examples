package egger.software.graphqlexamples;

import egger.software.graphqlexamples.entity.Flight;
import egger.software.graphqlexamples.repository.FlightsRepository;
import graphql.kickstart.servlet.GraphQLConfiguration;
import graphql.kickstart.servlet.GraphQLHttpServlet;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.util.Arrays;
import java.util.List;

public class GraphQLServlet extends GraphQLHttpServlet {

  private final FlightsRepository flightsRepository;

  public GraphQLServlet() {
    List<Flight> initialFlights = Arrays.asList(
            new Flight(1L, "OS2001", "VIE", "DRS"),
            new Flight(2L, "LH1234", "GRZ", "BER"),
            new Flight(3L, "KM6712", "MUN", "FRA")
    );
    this.flightsRepository = new FlightsRepository(initialFlights);
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
        .type("Query", builder ->
                builder.dataFetcher("flights", new FlightsDataFetcher(flightsRepository))
        )
        .build();

    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
  }

}