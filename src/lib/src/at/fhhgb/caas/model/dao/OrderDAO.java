package at.fhhgb.caas.model.dao;

import at.fhhgb.caas.data.Order;

import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 13.05.2015.
 */
public interface OrderDAO extends DataUpdateDAO {

    void addOrder(Order order) throws Exception;

    void removeOrder(Order order) throws Exception;

    List<Order> getOrders() throws Exception;

    void modifyOrder(Order order) throws Exception;

}
