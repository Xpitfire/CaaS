package at.fhhgb.caas.client.model.dao;

import at.fhhgb.caas.client.CaaSApp;
import at.fhhgb.caas.client.model.CommandExecutor;
import at.fhhgb.caas.client.model.DAOFactory;
import at.fhhgb.caas.data.Order;
import at.fhhgb.caas.model.dao.OrderDAO;
import at.fhhgb.caas.model.dao.OrderRMI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dinu Marius-Constantin on 29.05.2015.
 */
public class OrderDAORmiImpl implements OrderDAO {

    private static final Logger logger = Logger.getLogger(CaaSApp.CLIENT_LOGGER);

    private static final ObservableList<Order> orderList = FXCollections.observableArrayList();

    private final OrderRMI orderRMI;

    public OrderDAORmiImpl(OrderRMI orderRMI) {
        this.orderRMI = orderRMI;
    }

    @Override
    public void addOrder(Order order) throws Exception {
        Runnable runCmd = () -> {
            try {
                orderRMI.addOrder(order);
                Platform.runLater(() -> orderList.add(order));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not add order!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void removeOrder(Order order) throws Exception {
        Runnable runCmd = () -> {
            try {
                orderRMI.removeOrder(order);
                Platform.runLater(() -> orderList.remove(order));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not remove order!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public List<Order> getOrders() throws Exception {
        return orderList;
    }

    @Override
    public void modifyOrder(Order order) throws Exception {
        Runnable runCmd = () -> {
            try {
                orderRMI.modifyOrder(order);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not modify order!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void update() throws Exception {
        Runnable runCmd = () -> {
            try {
                List<Order> orders = orderRMI.getOrders();
                Platform.runLater(() -> orderList.setAll(orders));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not fetch order list!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }
}
