package at.fhhgb.caas.client.model;

import at.fhhgb.caas.client.CaaSApp;
import at.fhhgb.caas.client.socket.ServerHandler;
import at.fhhgb.caas.model.dao.DataUpdateDAO;
import at.fhhgb.caas.util.ThreadUtil;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dinu Marius-Constantin on 18.05.2015.
 */
public final class DataModelUpdate implements Runnable, Closeable {

    private static DataModelUpdate instance;

    private static final Logger logger = Logger.getLogger(CaaSApp.CLIENT_LOGGER);

    private boolean keepAlive;

    private static final long DELAY = 1000;
    private long timeout;

    private DataModelUpdate() {
        keepAlive = true;
        timeout = System.currentTimeMillis() + DELAY;
    }

    public synchronized static DataModelUpdate getInstance() {
        if (instance == null)
            instance = new DataModelUpdate();
        return instance;
    }

    public void update() {
        if (DAOFactory.getConnectionType() == DAOFactory.DAOFactoryConnectionType.RMI
                || ServerHandler.isConnected()) {
            Runnable runCmd = () -> {
                try {
                    DAOFactory.getCategoryDAO().update();
                    DAOFactory.getMealDAO().update();
                    DAOFactory.getOrderDAO().update();
                    DAOFactory.getPersonDAO().update();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, String.format("Could not update DAO instance %s"), e);
                }
            };
            CommandExecutor.offerCommand(runCmd);
        } else {
            if (timeout < System.currentTimeMillis()) {
                ServerHandler.connect();
                timeout = System.currentTimeMillis() + DELAY;
            }
        }
    }

    @Override
    public void run() {
        while (keepAlive) {
            update();
            ThreadUtil.sleep(ThreadUtil.SLEEP_200_MILLIS * 30);
        }
    }

    @Override
    public void close() {
        keepAlive = false;
    }
}
