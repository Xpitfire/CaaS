package at.fhhgb.caas.model.dao;

import at.fhhgb.caas.data.AuthType;
import at.fhhgb.caas.data.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 29.05.2015.
 */
public interface PersonRMI extends PersonDAO, DataUpdateRMI, Remote {

    @Override
    AuthType authenticateUserInput(String username, CharSequence password) throws RemoteException;

    @Override
    Person getPerson(String username) throws RemoteException;

    @Override
    boolean contains(String username) throws RemoteException;

    @Override
    boolean addPerson(Person person) throws RemoteException;

    @Override
    List<Person> getPersons() throws RemoteException;

    @Override
    void removePerson(Person person) throws RemoteException;

    @Override
    void modifyPerson(Person person) throws RemoteException;

}
