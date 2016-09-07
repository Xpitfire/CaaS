package at.fhhgb.caas.server.model.dao;

import at.fhhgb.caas.data.AuthType;
import at.fhhgb.caas.data.Person;
import at.fhhgb.caas.model.dao.DataPersistenceDAO;
import at.fhhgb.caas.model.dao.PersonDAO;
import at.fhhgb.caas.server.model.DAOFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public class PersonDAOFileImpl implements PersonDAO, DataPersistenceDAO<Person> {

    private static List<Person> users = new ArrayList<>();

    private static final String fileName = "person-data";

    @Override
    public AuthType authenticateUserInput(String username, CharSequence password) {
        synchronized (DAOFactory.SYNC_OBJECT) {
            return contains(username) && password.toString().contentEquals(getPerson(username).getPassword())
                    ? getPerson(username).getAuthType() : null;
        }
    }

    @Override
    public Person getPerson(String username) {
        synchronized (DAOFactory.SYNC_OBJECT) {
            return users.stream().filter(person -> person.getUsername().equals(username)).findFirst().get();
        }
    }

    @Override
    public boolean contains(String username) {
        synchronized (DAOFactory.SYNC_OBJECT) {
            return users.stream().anyMatch(person -> person.getUsername().equals(username));
        }
    }

    @Override
    public boolean addPerson(Person person) {
        if (person == null || contains(person.getUsername()))
            return false;
        synchronized (DAOFactory.SYNC_OBJECT) {
            users.add(person);
            return true;
        }
    }

    @Override
    public List<Person> getPersons() {
        synchronized (DAOFactory.SYNC_OBJECT) {
            return users;
        }
    }

    @Override
    public void removePerson(Person person) {
        if (person == null)
            return;

        synchronized (DAOFactory.SYNC_OBJECT) {
            if (person.getAuthType() == AuthType.ADMIN
                    && users.stream().filter(u -> u.getAuthType() == AuthType.ADMIN).count() == 1)
                return;
            users.remove(person);
        }
    }

    @Override
    public void modifyPerson(Person person) {
        if (person == null)
            return;

        synchronized (DAOFactory.SYNC_OBJECT) {
            Person user = users.stream()
                    .filter(u -> u.getUsername().equals(person.getUsername()))
                    .findFirst()
                    .get();
            if (user != null) {
                user.setPassword(person.getPassword());
                user.setFirstName(person.getFirstName());
                user.setLastName(person.getLastName());
                user.setEmail(person.getEmail());
                user.setUserStatus(person.getUserStatus());
                user.setProfilePicturePath(person.getProfilePicturePath());
            }
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
    public void initData(List<Person> data) {
        synchronized (DAOFactory.SYNC_OBJECT) {
            users = data;
        }
    }

}
