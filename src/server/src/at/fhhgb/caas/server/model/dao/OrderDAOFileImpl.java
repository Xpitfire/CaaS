package at.fhhgb.caas.server.model.dao;

import at.fhhgb.caas.data.Order;
import at.fhhgb.caas.model.dao.DataPersistenceDAO;
import at.fhhgb.caas.model.dao.OrderDAO;
import at.fhhgb.caas.server.model.DAOFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public class OrderDAOFileImpl implements OrderDAO, DataPersistenceDAO<Order> {

    private static List<Order> orderList = new ArrayList<>();

    private static final String fileName = "order-data";

    @Override
    public void addOrder(Order order) {
        synchronized (DAOFactory.SYNC_OBJECT) {
            if (!orderList.contains(order))
                orderList.add(order);
        }
    }

    @Override
    public void removeOrder(Order order) {
        synchronized (DAOFactory.SYNC_OBJECT) {
            orderList.remove(order);
        }
    }

    @Override
    public List<Order> getOrders() {
        synchronized (DAOFactory.SYNC_OBJECT) {
            return orderList;
        }
    }

    @Override
    public void modifyOrder(Order order) {
        if (order == null)
            return;
        synchronized (DAOFactory.SYNC_OBJECT) {
            orderList.stream()
                    .filter(o -> o.getOrderID().equals(order.getOrderID()))
                    .findFirst()
                    .ifPresent(o -> {
                        o.setDeliverHour(order.getDeliverHour());
                        o.setOrderStatus(order.getOrderStatus());
                        o.setPersonId(order.getPersonId());
                        o.setTotalPrice(order.getTotalPrice());
                        o.setMealList(order.getMealList());
                    });
        }
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void initData(List<Order> data) {
        synchronized (DAOFactory.SYNC_OBJECT) {
            orderList = data;
        }
    }

}
