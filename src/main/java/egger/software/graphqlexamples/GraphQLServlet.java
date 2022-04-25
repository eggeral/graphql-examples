package egger.software.graphqlexamples;

import graphql.kickstart.servlet.GraphQLConfiguration;
import graphql.kickstart.servlet.GraphQLHttpServlet;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

public class GraphQLServlet extends GraphQLHttpServlet {

  @Override
  protected GraphQLConfiguration getConfiguration() {
    return GraphQLConfiguration.with(createSchema()).build();
  }

  private GraphQLSchema createSchema() {
    String schema = "type Query{hello: String}";

    SchemaParser schemaParser = new SchemaParser();
    TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

    RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
        .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
        .build();

    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
  }

}