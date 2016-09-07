package at.fhhgb.caas.client.model;

import at.fhhgb.caas.client.CaaSApp;
import at.fhhgb.caas.data.Command;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created by Dinu Marius-Constantin on 19.05.2015.
 */
public final class CommandExecutor implements Runnable {

    private static final Logger logger = Logger.getLogger(CaaSApp.CLIENT_LOGGER);

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);
    private static BlockingQueue<Runnable> commandQueue = new ArrayBlockingQueue<>(100);
    private static boolean keepAlive;

    static {
        keepAlive = true;
        new Thread(new CommandExecutor()).start();
    }

    private CommandExecutor() {
    }

    public synchronized static void offerCommand(Runnable runnable) {
        try {
            commandQueue.put(runnable);
        } catch (InterruptedException e) {
            logger.severe("Failed to execute command!");
        }
    }

    public synchronized static void close() {
        keepAlive = false;
    }

    @Override
    public void run() {
        while (keepAlive) {
            try {
                executorService.submit(commandQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
