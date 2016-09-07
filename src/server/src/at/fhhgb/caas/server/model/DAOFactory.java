package at.fhhgb.caas.server.model;

import at.fhhgb.caas.data.Category;
import at.fhhgb.caas.data.Meal;
import at.fhhgb.caas.data.Order;
import at.fhhgb.caas.data.Person;
import at.fhhgb.caas.model.dao.*;
import at.fhhgb.caas.server.CaaSServerApp;
import at.fhhgb.caas.server.model.dao.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public class DAOFactory {

    public static final Object SYNC_OBJECT = new Object();

    private static CategoryDAO categoryDAO;
    private static MealDAO mealDAO;
    private static OrderDAO orderDAO;
    private static PersonDAO personDAO;

    private static CategoryDAOFileImpl categoryDAOFile;
    private static MealDAOFileImpl mealDAOFile;
    private static OrderDAOFileImpl orderDAOFile;
    private static PersonDAOFileImpl personDAOFile;

    private static CategoryDAODatabaseImpl categoryDAORmi;
    private static MealDAODatabaseImpl mealDAORmi;
    private static OrderDAODatabaseImpl orderDAORmi;
    private static PersonDAODatabaseImpl personDAORmi;

    private static final Logger logger = Logger.getLogger(CaaSServerApp.SERVER_LOGGER);

    public static final int RMI_PORT = 15002;
    public static final String SERVICE_DEFAULT_URL = "rmi://localhost:" + RMI_PORT + "/";
    public static final String CATEGORY_SERVICE_URL = SERVICE_DEFAULT_URL + "Category";
    public static final String MEAL_SERVICE_URL = SERVICE_DEFAULT_URL + "Meal";
    public static final String ORDER_SERVICE_URL = SERVICE_DEFAULT_URL + "Order";
    public static final String PERSON_SERVICE_URL = SERVICE_DEFAULT_URL + "Person";

    public enum DAOFactoryConnectionType {
        SOCKET,
        RMI
    }

    private static DAOFactoryConnectionType connectionType;

    public static final String DATABASE_CONNECTION_STRING = "jdbc:mysql://localhost:3306/caas";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "";
    private static Connection conn = null;

    private static DAOFactory instance;

    private DAOFactory() {
        connectionType = DAOFactoryConnectionType.RMI;

        if (connectionType == DAOFactoryConnectionType.RMI) {
            try {
                LocateRegistry.createRegistry(RMI_PORT);
            } catch (RemoteException e) {
                logger.log(Level.SEVERE, String.format("Could not register RMI registry on Port %d!", RMI_PORT), e);
            }
        }

        getCategoryDAO();
        getMealDAO();
        getOrderDAO();
        getPersonDAO();
    }

    public static DAOFactory createInstance() {
        if (instance == null)
            instance = new DAOFactory();
        return instance;
    }

    public synchronized static Connection getDatabaseConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(DATABASE_CONNECTION_STRING, DATABASE_USERNAME, DATABASE_PASSWORD);
            } catch (SQLException e) {
                logger.log(Level.SEVERE, String.format("Could not connect to database!"), e);
            }
        }
        return conn;
    }

    public synchronized static CategoryDAO getCategoryDAO() {
        if (categoryDAO == null) {
            if (connectionType == DAOFactoryConnectionType.SOCKET) {
                categoryDAO = categoryDAOFile = new CategoryDAOFileImpl();
            } else if (connectionType == DAOFactoryConnectionType.RMI) {
                categoryDAO = categoryDAORmi = new CategoryDAODatabaseImpl();
                try {
                    Remote stub = UnicastRemoteObject.exportObject(categoryDAORmi, RMI_PORT);
                    Naming.rebind(CATEGORY_SERVICE_URL, stub);
                } catch (RemoteException | MalformedURLException e) {
                    logger.log(Level.SEVERE, String.format("Could not register CategoryRMI to service on Port %d!", RMI_PORT), e);
                }
            }
        }
        return categoryDAO;
    }

    public synchronized static MealDAO getMealDAO() {
        if (mealDAO == null) {
            if (connectionType == DAOFactoryConnectionType.SOCKET) {
                mealDAO = mealDAOFile = new MealDAOFileImpl();
            } else if (connectionType == DAOFactoryConnectionType.RMI) {
                mealDAO = mealDAORmi = new MealDAODatabaseImpl();
                try {
                    Remote stub = UnicastRemoteObject.exportObject(mealDAORmi, RMI_PORT);
                    Naming.rebind(MEAL_SERVICE_URL, stub);
                } catch (RemoteException | MalformedURLException e) {
                    logger.log(Level.SEVERE, String.format("Could not register MealRMI to service on Port %d!", RMI_PORT), e);
                }
            }
        }
        return mealDAO;
    }

    public synchronized static OrderDAO getOrderDAO() {
        if (orderDAO == null) {
            if (connectionType == DAOFactoryConnectionType.SOCKET) {
                orderDAO = orderDAOFile = new OrderDAOFileImpl();
            } else if (connectionType == DAOFactoryConnectionType.RMI) {
                orderDAO = orderDAORmi = new OrderDAODatabaseImpl();
                try {
                    Remote stub = UnicastRemoteObject.exportObject(orderDAORmi, RMI_PORT);
                    Naming.rebind(ORDER_SERVICE_URL, stub);
                } catch (RemoteException | MalformedURLException e) {
                    logger.log(Level.SEVERE, String.format("Could not register OrderRMI to service on Port %d!", RMI_PORT), e);
                }
            }
        }
        return orderDAO;
    }

    public synchronized static PersonDAO getPersonDAO() {
        if (personDAO == null) {
            if (connectionType == DAOFactoryConnectionType.SOCKET) {
                personDAO = personDAOFile = new PersonDAOFileImpl();
            } else if (connectionType == DAOFactoryConnectionType.RMI) {
                personDAO = personDAORmi = new PersonDAODatabaseImpl();
                try {
                    Remote stub = UnicastRemoteObject.exportObject(personDAORmi, RMI_PORT);
                    Naming.rebind(PERSON_SERVICE_URL, stub);
                } catch (RemoteException | MalformedURLException e) {
                    logger.log(Level.SEVERE, String.format("Could not register PersonRMI to service on Port %d!", RMI_PORT), e);
                }
            }
        }
        return personDAO;
    }

    public synchronized static void save() {
        if (connectionType == DAOFactoryConnectionType.SOCKET) {
            try {
                DataModelFileSerializer.writeFile(getCategoryDAO().getCategories(), categoryDAOFile.getFileName());
                DataModelFileSerializer.writeFile(getMealDAO().getMeals(), mealDAOFile.getFileName());
                DataModelFileSerializer.writeFile(getOrderDAO().getOrders(), orderDAOFile.getFileName());
                DataModelFileSerializer.writeFile(getPersonDAO().getPersons(), personDAOFile.getFileName());
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not save data to file!", e);
            }
        }
    }

    public synchronized static void load() {
        if (connectionType == DAOFactoryConnectionType.SOCKET) {
            List<Category> categories = DataModelFileSerializer.readFile(categoryDAOFile.getFileName());
            categoryDAOFile.initData(categories);

            List<Meal> meals = DataModelFileSerializer.readFile(mealDAOFile.getFileName());
            mealDAOFile.initData(meals);

            List<Order> orders = DataModelFileSerializer.readFile(orderDAOFile.getFileName());
            orderDAOFile.initData(orders);

            List<Person> persons = DataModelFileSerializer.readFile(personDAOFile.getFileName());
            personDAOFile.initData(persons);
        }
    }

}
