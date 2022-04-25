package egger.software.graphqlexamples;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static Server startServer(int port) throws Exception {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");

        contextHandler.addServlet(GraphQLServlet.class, "/graphql/*");

        Server server = new Server(port);
        server.setHandler(contextHandler);

        server.start();
        return server;
    }

    public static void main(String[] args) {
        try {

            Server server = startServer(80);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println("Shutting down the server...");
                    server.stop();
                    System.out.println("Done.");
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }));

            System.out.println("Server started.\nCTRL+C to exit.");
            server.join();

        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

}