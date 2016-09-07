package at.fhhgb.caas.client.model.dao;

import at.fhhgb.caas.client.CaaSApp;
import at.fhhgb.caas.client.model.CommandExecutor;
import at.fhhgb.caas.client.model.DAOFactory;
import at.fhhgb.caas.data.AuthType;
import at.fhhgb.caas.data.Person;
import at.fhhgb.caas.model.dao.PersonDAO;
import at.fhhgb.caas.model.dao.PersonRMI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dinu Marius-Constantin on 29.05.2015.
 */
public class PersonDAORmiImpl implements PersonDAO {

    private static final Logger logger = Logger.getLogger(CaaSApp.CLIENT_LOGGER);

    private static final ObservableList<Person> users = FXCollections.observableArrayList();

    private final PersonRMI personRMI;

    public PersonDAORmiImpl(PersonRMI personRMI) {
        this.personRMI = personRMI;
    }

    @Override
    public AuthType authenticateUserInput(String username, CharSequence password) throws Exception {
        return contains(username) && password.toString().contentEquals(getPerson(username).getPassword())
                ? getPerson(username).getAuthType() : null;
    }

    @Override
    public Person getPerson(String username) throws Exception {
        return users.stream().filter(person -> person.getUsername().equals(username)).findFirst().get();
    }

    @Override
    public boolean contains(String username) throws Exception {
        return users.stream().anyMatch(person -> person.getUsername().equals(username));
    }

    @Override
    public boolean addPerson(Person person) throws Exception {
        Runnable runCmd = () -> {
            try {
                personRMI.addPerson(person);
                Platform.runLater(() -> users.add(person));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not add person!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
        return true;
    }

    @Override
    public List<Person> getPersons() throws Exception {
        return users;
    }

    @Override
    public void removePerson(Person person) throws Exception {
        Runnable runCmd = () -> {
            try {
                personRMI.removePerson(person);
                Platform.runLater(() -> users.remove(person));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not remove person!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void modifyPerson(Person person) throws Exception {
        Runnable runCmd = () -> {
            try {
                personRMI.modifyPerson(person);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not modify person!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void update() throws Exception {
        Runnable runCmd = () -> {
            try {
                List<Person> persons = personRMI.getPersons();
                Platform.runLater(() -> users.setAll(persons));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not fetch user list!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }

}
