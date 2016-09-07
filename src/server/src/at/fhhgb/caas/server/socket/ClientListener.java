package at.fhhgb.caas.server.socket;

import at.fhhgb.caas.server.CaaSServerApp;
import at.fhhgb.caas.util.ThreadUtil;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public class ClientListener implements Runnable, AutoCloseable {

    public static final int PORT = 15001;
    public static final long TTL = 60000;

    private static final Object SYNC_OBJECT = new Object();
    private static long clientId = 0L;

    private static ClientListener instance;

    private Logger logger = Logger.getLogger(CaaSServerApp.SERVER_LOGGER);

    private List<Long> kickList = new ArrayList<>();
    private Map<Long, ClientHandler> clientHandlerMap = new HashMap<>();
    private ExecutorService clientExecutor = Executors.newFixedThreadPool(2);
    private ServerSocket serverSocket;

    private boolean keepAlive;

    public static ClientListener getInstance() {
        if (instance == null)
            instance = new ClientListener();
        return instance;
    }

    private ClientListener() {
        keepAlive = true;
        try {
            serverSocket = ServerSocketFactory.getDefault().createServerSocket(PORT);
            new Thread(new ClientMonitor()).start();
            logger.log(Level.INFO, "Successfully created Server! Listening on PORT: {0}", PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (keepAlive) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(++clientId, socket);
                clientExecutor.submit(clientHandler);
                synchronized (SYNC_OBJECT) {
                    clientHandlerMap.put(clientId, clientHandler);
                }
                logger.log(Level.INFO, "Successfully added new user: {0}", clientId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientMonitor implements Runnable {
        @Override
        public void run() {
            while (keepAlive) {
                synchronized (SYNC_OBJECT) {
                    kickList.clear();
                    clientHandlerMap.forEach((id, client) -> {
                        if (System.currentTimeMillis() > client.getTTLTimer()) {
                            client.close();
                            kickList.add(id);
                            logger.log(Level.INFO, String.format("Kicked client %d, due to TTL exceeded!", client.getClientId()));
                        }
                    });
                    kickList.forEach(id -> clientHandlerMap.remove(id));
                }
                ThreadUtil.sleep(ThreadUtil.SLEEP_200_MILLIS * 50);
            }
        }
    }

    @Override
    public void close() {
        keepAlive = false;
        synchronized (SYNC_OBJECT) {
            clientHandlerMap.forEach((id, handler) -> handler.close());
        }
    }
}
