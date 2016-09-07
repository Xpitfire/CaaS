package at.fhhgb.caas.model.dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Dinu Marius-Constantin on 29.05.2015.
 */
public interface DataUpdateRMI extends DataUpdateDAO, Remote {

    @Override
    void update() throws RemoteException;
}
