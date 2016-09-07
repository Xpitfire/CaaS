package at.fhhgb.caas.model.dao;

import at.fhhgb.caas.data.Category;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 27.05.2015.
 */
public interface CategoryRMI extends CategoryDAO, DataUpdateRMI, Remote {

    @Override
    void addCategory(Category category) throws RemoteException;

    @Override
    void removeCategory(Category category)  throws RemoteException;

    @Override
    void modifyCategory(Category category)  throws RemoteException;

    @Override
    List<Category> getCategories() throws RemoteException;

}
