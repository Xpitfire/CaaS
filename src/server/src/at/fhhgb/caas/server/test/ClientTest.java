package at.fhhgb.caas.server.test;

import at.fhhgb.caas.data.Category;
import at.fhhgb.caas.data.Command;
import at.fhhgb.caas.util.ThreadUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Dinu Marius-Constantin on 18.05.2015.
 */
public class ClientTest {

    @Test
    public void testClientConnection() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 15001);
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ThreadUtil.sleep(2000);
        socket.close();
    }

    @Test
    public void testClientWriteTest() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 15001);
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(new Command<>(Command.Operation.GET, new Category("Test")));
        outputStream.flush();
        ThreadUtil.sleep(2000);
        outputStream.close();
        socket.close();
    }

}
