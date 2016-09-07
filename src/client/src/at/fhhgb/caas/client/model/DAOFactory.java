package at.fhhgb.caas.client.model;

import at.fhhgb.caas.client.CaaSApp;
import at.fhhgb.caas.client.model.dao.*;
import at.fhhgb.caas.model.dao.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public final class DAOFactory {

    private static CategoryRMI categoryRMI;
    private static MealRMI mealRMI;
    private static OrderRMI orderRMI;
    private static PersonRMI personRMI;

    private static CategoryDAO categoryDAO;
    private static MealDAO mealDAO;
    private static OrderDAO orderDAO;
    private static PersonDAO personDAO;

    private static final Logger logger = Logger.getLogger(CaaSApp.CLIENT_LOGGER);

    public static final int RMI_PORT = 15002;

    public static final String DEFAULT_RMI_URL = "rmi://localhost:" + RMI_PORT + "/";
    public static final String RMI_CATEGORY_URL = DEFAULT_RMI_URL + "Category";
    public static final String RMI_MEAL_URL = DEFAULT_RMI_URL + "Meal";
    public static final String RMI_ORDER_URL = DEFAULT_RMI_URL + "Order";
    public static final String RMI_PERSON_URL = DEFAULT_RMI_URL + "Person";

    public enum DAOFactoryConnectionType {
        SOCKET,
        RMI
    }

    private static DAOFactoryConnectionType connectionType;

    static {
        connectionType = DAOFactoryConnectionType.RMI;
    }

    private DAOFactory() {
    }

    public static DAOFactoryConnectionType getConnectionType() {
        return connectionType;
    }

    public static CategoryDAO getCategoryDAO() {
        if (categoryDAO == null) {
            if (connectionType == DAOFactoryConnectionType.SOCKET) {
                categoryDAO = new CategoryDAOSocketImpl();
            } else if (connectionType == DAOFactoryConnectionType.RMI) {
                try {
                    categoryRMI = (CategoryRMI) Naming.lookup(RMI_CATEGORY_URL);
                    categoryDAO = new CategoryDAORmiImpl(categoryRMI);
                } catch (MalformedURLException | RemoteException | NotBoundException e) {
                    logger.log(Level.SEVERE, "Could not connect to RMI interface!", e);
                }
            }
        }
        return categoryDAO;
    }

    public static MealDAO getMealDAO() {
        if (mealDAO == null) {
            if (connectionType == DAOFactoryConnectionType.SOCKET) {
                mealDAO = new MealDAOSocketImpl();
            } else if (connectionType == DAOFactoryConnectionType.RMI) {
                try {
                    mealRMI = (MealRMI) Naming.lookup(RMI_MEAL_URL);
                    mealDAO = new MealDAORmiImpl(mealRMI);
                } catch (MalformedURLException | RemoteException | NotBoundException e) {
                    logger.log(Level.SEVERE, "Could not connect to RMI interface!", e);
                }
            }
        }
        return mealDAO;
    }

    public static OrderDAO getOrderDAO() {
        if (orderDAO == null) {
            if (connectionType == DAOFactoryConnectionType.SOCKET) {
                orderDAO = new OrderDAOSocketImpl();
            } else if (connectionType == DAOFactoryConnectionType.RMI) {
                try {
                    orderRMI = (OrderRMI) Naming.lookup(RMI_ORDER_URL);
                    orderDAO = new OrderDAORmiImpl(orderRMI);
                } catch (MalformedURLException | RemoteException | NotBoundException e) {
                    logger.log(Level.SEVERE, "Could not connect to RMI interface!", e);
                }
            }
        }
        return orderDAO;
    }

    public static PersonDAO getPersonDAO() {
        if (personDAO == null) {
            if (connectionType == DAOFactoryConnectionType.SOCKET) {
                personDAO = new PersonDAOSocketImpl();
            } else if (connectionType == DAOFactoryConnectionType.RMI) {
                try {
                    personRMI = (PersonRMI) Naming.lookup(RMI_PERSON_URL);
                    personDAO = new PersonDAORmiImpl(personRMI);
                } catch (MalformedURLException | RemoteException | NotBoundException e) {
                    logger.log(Level.SEVERE, "Could not connect to RMI interface!", e);
                }
            }
        }
        return personDAO;
    }

}
