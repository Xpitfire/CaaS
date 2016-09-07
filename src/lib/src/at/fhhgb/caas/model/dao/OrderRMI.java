package at.fhhgb.caas.model.dao;

import at.fhhgb.caas.data.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 29.05.2015.
 */
public interface OrderRMI extends OrderDAO, DataUpdateRMI, Remote {

    @Override
    void addOrder(Order order) throws RemoteException;

    @Override
    void removeOrder(Order order) throws RemoteException;

    @Override
    List<Order> getOrders() throws RemoteException;

    @Override
    void modifyOrder(Order order) throws RemoteException;

}
