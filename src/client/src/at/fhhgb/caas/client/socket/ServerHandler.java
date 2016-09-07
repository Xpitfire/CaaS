package at.fhhgb.caas.client.socket;

import at.fhhgb.caas.client.CaaSApp;
import at.fhhgb.caas.client.model.DAOFactory;
import at.fhhgb.caas.client.model.DataModelUpdate;
import at.fhhgb.caas.data.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public class ServerHandler implements AutoCloseable {

    public static final int PORT = 15001;

    private static final Logger logger = Logger.getLogger(CaaSApp.CLIENT_LOGGER);

    private static ServerHandler instance;

    private static Socket socket;
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;

    private static DataModelUpdate dataModelUpdate;

    static {
        dataModelUpdate = DataModelUpdate.getInstance();
        new Thread(dataModelUpdate).start();
    }

    private ServerHandler() {
        if (DAOFactory.getConnectionType() == DAOFactory.DAOFactoryConnectionType.SOCKET) {
            try {
                socket = new Socket(InetAddress.getLocalHost(), PORT);
                if (socket.isConnected()) {
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    inputStream = new ObjectInputStream(socket.getInputStream());
                    logger.info("Successfully connected to server!");
                } else {
                    logger.severe("Failed to connect to server!");
                }
                logger.info("Client initialization complete!");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Could not connect to server!", e);
            }
        }
    }

    public synchronized static ServerHandler getInstance() {
        if (instance == null)
            instance = new ServerHandler();
        return instance;
    }

    public synchronized static boolean isConnected() {
        return (socket != null && socket.isConnected()) ? true : false;
    }

    public synchronized static void connect() {
        if (instance != null)
            instance.close();
        instance = new ServerHandler();
    }

    public synchronized <S, T extends Command<S>, V, U extends Command<V>> U callback(T o) {
        U rsp = null;
        try {
            outputStream.writeObject(o);
            if (o.getOperation() == Command.Operation.GET
                    || o.getOperation() == Command.Operation.GET_ALL
                    || o.getOperation() == Command.Operation.CONTAINS)
                rsp = (U) inputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return rsp;
    }

    @Override
    public void close() {
        try {
            if (outputStream != null)
                outputStream.close();
            outputStream = null;
            logger.warning("Closed output stream!");
        } catch (IOException e) {
            logger.warning("Output stream failed to close!");
        }
        try {
            if (inputStream != null)
                inputStream.close();
            inputStream = null;
            logger.warning("Closed input stream!");
        } catch (IOException e) {
            logger.warning("Input stream failed to close!");
        }
        try {
            if (socket != null)
                socket.close();
            socket = null;
            logger.warning("Closed socket!");
        } catch (IOException e) {
            logger.warning("Socket failed to close!");
        }
    }

}
