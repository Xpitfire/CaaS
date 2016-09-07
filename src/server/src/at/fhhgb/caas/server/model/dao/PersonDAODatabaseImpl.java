package at.fhhgb.caas.server.model.dao;

import at.fhhgb.caas.data.AuthType;
import at.fhhgb.caas.data.Person;
import at.fhhgb.caas.data.UserStatus;
import at.fhhgb.caas.model.dao.PersonRMI;
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
public class PersonDAODatabaseImpl implements PersonRMI {

    private static final Logger logger = Logger.getLogger(CaaSServerApp.SERVER_LOGGER);

    private int getAuthTypeId(Person person) throws SQLException {
        try (Statement statement = DAOFactory.getDatabaseConnection().createStatement();
             ResultSet authTypeResultSet = statement.executeQuery(String.format("SELECT id FROM authtype WHERE name LIKE '%s';", person.getAuthType().toString()))) {
            if (authTypeResultSet.next())
                return authTypeResultSet.getInt("id");
        }
        return -1;
    }

    private int getUserStatusId(Person person) throws SQLException {
        try (Statement statement = DAOFactory.getDatabaseConnection().createStatement();
             ResultSet userStatusResultSet = statement.executeQuery(String.format("SELECT id FROM userstatus WHERE name LIKE '%s';", person.getUserStatus().toString()))) {
            if (userStatusResultSet.next())
                return userStatusResultSet.getInt("id");
        }
        return -1;
    }

    @Override
    public AuthType authenticateUserInput(String username, CharSequence password) throws RemoteException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Person getPerson(String username) throws RemoteException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(String username) throws RemoteException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addPerson(Person person) throws RemoteException {
        if (person != null) {
            try (Statement statement = DAOFactory.getDatabaseConnection().createStatement()) {
                statement.execute(String.format(
                        "INSERT INTO person (id, username, password, authType, userStatus, firstName, lastName, profilePicturePath, email) " +
                                "VALUES ('%s', '%s', '%s', %d, %d, '%s', '%s', '%s', '%s');",
                        person.getId(), person.getUsername(), person.getPassword(), getAuthTypeId(person), getUserStatusId(person), person.getFirstName(),
                        person.getLastName(), person.getProfilePicturePath(), person.getEmail()));
                return true;
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Could not execute statement!", e);
            }
        }
        return false;
    }

    @Override
    public List<Person> getPersons() throws RemoteException {
        List<Person> persons = new ArrayList<>();
        try (Statement statement = DAOFactory.getDatabaseConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT p.id, p.username, p.password, a.name AS authtype, u.name AS userstatus, p.firstName, p.lastName, p.profilePicturePath, p.email\n" +
                    "  FROM person p, userstatus u, authtype a\n" +
                    "  WHERE p.authType = a.id AND p.userStatus = u.id;");
            while (resultSet.next()) {
                Person p = new Person(resultSet.getString("username"), resultSet.getString("id"), resultSet.getString("password"), AuthType.getType(resultSet.getString("authtype")));
                p.setUserStatus(UserStatus.getType(resultSet.getString("userstatus")));
                p.setFirstName(resultSet.getString("firstName"));
                p.setLastName(resultSet.getString("lastName"));
                p.setEmail(resultSet.getString("email"));
                p.setProfilePicturePath(resultSet.getString("profilePicturePath"));
                persons.add(p);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Could not execute statement!", e);
        }
        return persons;
    }

    @Override
    public void removePerson(Person person) throws RemoteException {
        if (person != null) {
            try (Statement statement = DAOFactory.getDatabaseConnection().createStatement()) {
                statement.execute(String.format("DELETE FROM person WHERE username LIKE '%s';", person.getUsername()));
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Could not execute statement!", e);
            }
        }
    }

    @Override
    public void modifyPerson(Person person) throws RemoteException {
        if (person != null) {
            try (Statement statement = DAOFactory.getDatabaseConnection().createStatement()) {
                statement.execute(String.format(
                        "UPDATE person SET password = '%s', authType = %d, userStatus = %d, firstName = '%s', lastName = '%s', profilePicturePath = '%s', email = '%s' WHERE username = '%s';",
                        person.getPassword(),
                        getAuthTypeId(person),
                        getUserStatusId(person),
                        person.getFirstName(),
                        person.getLastName(),
                        person.getProfilePicturePath(),
                        person.getEmail(),
                        person.getUsername()));
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Could not execute statement!", e);
            }
        }
    }

    @Override
    public void update() throws RemoteException {
        throw new UnsupportedOperationException();
    }
}
