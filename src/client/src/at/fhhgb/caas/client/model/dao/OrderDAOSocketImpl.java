package at.fhhgb.caas.client.model.dao;

import at.fhhgb.caas.client.model.CommandExecutor;
import at.fhhgb.caas.client.socket.ServerHandler;
import at.fhhgb.caas.data.Command;
import at.fhhgb.caas.data.Order;
import at.fhhgb.caas.model.dao.DataUpdateDAO;import at.fhhgb.caas.model.dao.OrderDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 13.05.2015.
 */
public class OrderDAOSocketImpl implements OrderDAO {

    private static final ObservableList<Order> orderList = FXCollections.observableArrayList();

    @Override
    public void addOrder(Order order) {
        Platform.runLater(() -> orderList.add(order));
        Runnable runCmd = () -> ServerHandler.getInstance().callback(new Command<>(Command.Operation.INSERT, order));
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void removeOrder(Order order) {
        Platform.runLater(() -> orderList.remove(order));
        Runnable runCmd = () -> ServerHandler.getInstance().callback(new Command<>(Command.Operation.DELETE, order));
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public ObservableList<Order> getOrders() {
        return orderList;
    }

    @Override
    public void modifyOrder(Order order) {
        Runnable runCmd = () -> ServerHandler.getInstance().callback(new Command<>(Command.Operation.MODIFY, order));
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void update() {
        Runnable runCmd = () -> {
            Command<List<Order>> command = ServerHandler.getInstance().callback(
                    new Command<>(Command.Operation.GET_ALL, Command.ObjectType.ORDER));
            if (command.getData() != null)
                Platform.runLater(() -> orderList.setAll(command.getData()));
        };
        CommandExecutor.offerCommand(runCmd);
    }
}
