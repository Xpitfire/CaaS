package at.fhhgb.caas.server.model.dao;

import at.fhhgb.caas.data.*;
import at.fhhgb.caas.model.dao.OrderRMI;
import at.fhhgb.caas.server.CaaSServerApp;
import at.fhhgb.caas.server.model.DAOFactory;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dinu Marius-Constantin on 29.05.2015.
 */
public class OrderDAODatabaseImpl implements OrderRMI {

    private static final Logger logger = Logger.getLogger(CaaSServerApp.SERVER_LOGGER);

    @Override
    public void addOrder(Order order) throws RemoteException {
        if (order != null) {
            try (Statement orderStatusStatement = DAOFactory.getDatabaseConnection().createStatement();
                 ResultSet orderStatusResultSet = orderStatusStatement.executeQuery(String.format("SELECT id FROM orderstatus WHERE name LIKE '%s'", order.getOrderStatus().getName()))) {
                if (orderStatusResultSet.next()) {
                    int orderStatusId = orderStatusResultSet.getInt("id");
                    try (Statement statement = DAOFactory.getDatabaseConnection().createStatement();
                         ResultSet resultSet = statement.executeQuery("SELECT MAX(orderId) AS lastId FROM `order`;")) {
                        if (resultSet.next()) {
                            int orderId = resultSet.getInt("lastId") + 1;
                            for (Meal m : order.getMealList()) {
                                statement.execute(String.format(
                                        "INSERT INTO `order` (orderId, personId, meal, deliverHour, orderStatus) VALUES (%d, '%s', '%s', '%s', %d);",
                                        orderId, order.getPersonId(), m.getName(), order.getDeliverHour(), orderStatusId));
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Could not execute statement!", e);
            }
        }
    }

    @Override
    public void removeOrder(Order order) throws RemoteException {
        if (order != null) {
            try (Statement statement = DAOFactory.getDatabaseConnection().createStatement()) {
                statement.execute(String.format("DELETE FROM `order` WHERE orderId = %s;", order.getOrderID()));
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Could not execute statement!", e);
            }
        }
    }

    @Override
    public List<Order> getOrders() throws RemoteException {
        List<Order> orders = new ArrayList<>();
        try (Statement statement = DAOFactory.getDatabaseConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT o.orderId, o.personId, o.deliverHour, s.name AS orderStatus, m.name AS meal, m.category, m.price, w.name AS weekday"
                     + "  FROM `order` o, meal m, weekday w, orderstatus s"
                     + "  WHERE o.meal = m.name AND m.weekday = w.id AND o.orderStatus = s.id;")) {
            while (resultSet.next()) {
                Order o = new Order(resultSet.getString("personId"), String.valueOf(resultSet.getInt("orderId")));
                o.setDeliverHour(resultSet.getString("deliverHour"));
                o.setOrderStatus(OrderStatus.getType(resultSet.getString("orderStatus")));
                if (orders.contains(o)) {
                    o = orders.get(orders.indexOf(o));
                    Meal m = new Meal(resultSet.getString("meal"));
                    boolean containsMeal = o.getMealList().contains(m);
                    if (containsMeal)
                        m = o.getMealList().get(o.getMealList().indexOf(m));
                    m.addCategory(new Category(resultSet.getString("category")));
                    m.addAvailableDay(WeekDay.getType(resultSet.getString("weekday")));
                    if (!containsMeal) {
                        m.setPrice(resultSet.getDouble("price"));
                        o.addMeal(m);
                    }
                } else {
                    Meal m = new Meal(resultSet.getString("meal"));
                    m.addCategory(new Category(resultSet.getString("category")));
                    m.addAvailableDay(WeekDay.getType(resultSet.getString("weekday")));
                    m.setPrice(resultSet.getDouble("price"));
                    orders.add(o);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Could not execute statement!", e);
        }
        return orders;
    }

    @Override
    public void modifyOrder(Order order) throws RemoteException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update() throws RemoteException {
        throw new UnsupportedOperationException();
    }
}
