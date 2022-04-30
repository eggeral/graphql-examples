package egger.software.graphqlexamples;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GraphQLTests {

    private static Server server;
    private static Client client;
    private static final int port = 8091;

    @Before
    public void startServer() throws Exception {
        server = Main.startServer(port);
        client = ClientBuilder.newClient();
        HttpAuthenticationFeature authenticationFeature = HttpAuthenticationFeature.basicBuilder().build();
        client.register(authenticationFeature);
    }

    @After
    public void stopServer() throws Exception {
        client.close();
        server.stop();
    }

    @Test
    public void run_basic_mutation() {
        WebTarget target = client.target("http://localhost:" + port).path("graphql");

        String query = "mutation{createFlight(flightInput: {number: \"LH7689\", from: \"CHX\", to: \"AUS\"}) {id}}";

        Response response = target.request().post(Entity.entity(query, "application/graphql"));
        String result = response.readEntity(String.class);
        System.out.println(result);
        assertThat(response.getStatus(), is(200));
        assertThat(result, is(equalTo("{\"data\":{\"createFlight\":{\"id\":\"4\"}}}")));

        query = "mutation {\n" +
                "    addPassenger(flightId: 4,passengerInput: {\n" +
                "        name: \"Helga Kirschbaum\"\n" +
                "    }) {\n" +
                "        id\n" +
                "    }\n" +
                "}";

        response = target.request().post(Entity.entity(query, "application/graphql"));
        result = response.readEntity(String.class);
        System.out.println(result);
        assertThat(response.getStatus(), is(200));
        assertThat(result, is(equalTo("{\"data\":{\"addPassenger\":{\"id\":\"4\"}}}")));


        query = "{flights(from:\"CHX\"){passengers{name}}}";

        response = target.request().post(Entity.entity(query, "application/graphql"));
        result = response.readEntity(String.class);
        System.out.println(result);
        assertThat(response.getStatus(), is(200));
        assertThat(result, is(equalTo("{\"data\":{\"flights\":[{\"passengers\":[{\"name\":\"Helga Kirschbaum\"}]}]}}")));

        query = "mutation {\n" +
                "    flight(number: \"LH7689\") {\n" +
                "        addPassenger(passengerInput: {\n" +
                "            name: \"Einer Noch\"\n" +
                "        }) {\n" +
                "            id\n" +
                "        }\n" +
                "    }\n" +
                "}";

        response = target.request().post(Entity.entity(query, "application/graphql"));
        result = response.readEntity(String.class);
        System.out.println(result);
        assertThat(response.getStatus(), is(200));
        assertThat(result, is(equalTo("{\"data\":{\"flight\":{\"addPassenger\":{\"id\":\"5\"}}}}")));

        query = "{flights(from:\"CHX\"){passengers{name}}}";

        response = target.request().post(Entity.entity(query, "application/graphql"));
        result = response.readEntity(String.class);
        System.out.println(result);
        assertThat(response.getStatus(), is(200));
        assertThat(result, is(equalTo("{\"data\":{\"flights\":[" + "{\"passengers\":[" +
                "{\"name\":\"Helga Kirschbaum\"}," +
                "{\"name\":\"Einer Noch\"}" +
                "]}]}}")));

    }

}
