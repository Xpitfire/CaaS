package at.fhhgb.caas.client.model.dao;

import at.fhhgb.caas.client.model.CommandExecutor;
import at.fhhgb.caas.client.socket.ServerHandler;
import at.fhhgb.caas.data.AuthType;
import at.fhhgb.caas.data.Command;
import at.fhhgb.caas.data.Person;
import at.fhhgb.caas.model.dao.DataUpdateDAO;
import at.fhhgb.caas.model.dao.PersonDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 12.05.2015.
 */
public class PersonDAOSocketImpl implements PersonDAO {

    private static final ObservableList<Person> users = FXCollections.observableArrayList();

    @Override
    public AuthType authenticateUserInput(String username, CharSequence password) {
        return contains(username) && password.toString().contentEquals(getPerson(username).getPassword())
                ? getPerson(username).getAuthType() : null;
    }

    @Override
    public Person getPerson(String username) {
        return users.stream().filter(person -> person.getUsername().equals(username)).findFirst().get();
    }

    @Override
    public boolean contains(String username) {
        return users.stream().anyMatch(person -> person.getUsername().equals(username));
    }

    @Override
    public boolean addPerson(Person person) {
        Runnable runCmd = () -> {
            ServerHandler.getInstance().callback(new Command<>(Command.Operation.INSERT, person));
            Command<Person> command = ServerHandler.getInstance().callback(new Command<>(Command.Operation.GET, person));
            if (command == null)
                Platform.runLater(() -> users.add(person));
        };
        CommandExecutor.offerCommand(runCmd);
        return true;
    }

    @Override
    public ObservableList<Person> getPersons() {
        return users;
    }

    @Override
    public void removePerson(Person person) {
        Platform.runLater(() -> users.remove(person));
        Runnable runCmd = () -> ServerHandler.getInstance().callback(new Command<>(Command.Operation.DELETE, person));
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void modifyPerson(Person person) {
        Runnable runCmd = () -> ServerHandler.getInstance().callback(new Command<>(Command.Operation.MODIFY, person));
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void update() {
        Runnable runCmd = () -> {
            Command<List<Person>> command = ServerHandler.getInstance().callback(
                    new Command<>(Command.Operation.GET_ALL, Command.ObjectType.PERSON));
            if (command.getData() != null)
                Platform.runLater(() -> users.setAll(command.getData()));
        };
        CommandExecutor.offerCommand(runCmd);
    }

}
