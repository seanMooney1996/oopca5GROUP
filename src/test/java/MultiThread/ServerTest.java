package MultiThread;

import Databases.Daos.MySqlMovieDao;
import Server_Client.Client;
import Server_Client.Server;
import junit.framework.TestCase;

public class ServerTest extends TestCase {

    public void testStart() {
        Server server = new Server();
        server.start();
        assertTrue(true);
    }

    public void testHandleGetMovieByID() {

    }
}