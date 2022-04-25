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
    public void run_basic_query() {
        WebTarget target = client.target("http://localhost:" + port).path("/graphql");
        Response response = target
                .queryParam("query",
                        "{hello}".replaceAll("\\{", "%7B").replaceAll("\\}", "%7D"))
                .request().get();
        String result = response.readEntity(String.class);
        System.out.println(result);
        assertThat(response.getStatus(), is(200));
        assertThat(result, is(equalTo("{\"data\":{\"hello\":\"world\"}}")));

        response = target.request().post(Entity.entity(
                "{" +
                        "\"query\": \"{hello}\"" +
                        "}", MediaType.APPLICATION_JSON_TYPE));
        result = response.readEntity(String.class);
        System.out.println(result);
        assertThat(response.getStatus(), is(200));
        assertThat(result, is(equalTo("{\"data\":{\"hello\":\"world\"}}")));

        response = target.request().post(Entity.entity(
                "{hello}", "application/graphql"));
        result = response.readEntity(String.class);
        System.out.println(result);
        assertThat(response.getStatus(), is(200));
        assertThat(result, is(equalTo("{\"data\":{\"hello\":\"world\"}}")));
    }

}
