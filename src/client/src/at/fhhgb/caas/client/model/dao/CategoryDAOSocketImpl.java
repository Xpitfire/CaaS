package at.fhhgb.caas.client.model.dao;

import at.fhhgb.caas.client.model.CommandExecutor;
import at.fhhgb.caas.client.socket.ServerHandler;
import at.fhhgb.caas.data.Command;
import at.fhhgb.caas.model.dao.CategoryDAO;
import at.fhhgb.caas.model.dao.DataUpdateDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import at.fhhgb.caas.data.Category;

import java.util.List;


/**
 * Created by Dinu Marius-Constantin on 13.05.2015.
 */
public class CategoryDAOSocketImpl implements CategoryDAO {

    private static final ObservableList<Category> categories = FXCollections.observableArrayList();


    @Override
    public void addCategory(Category category) {
        Platform.runLater(() -> categories.add(category));
        Runnable runCmd = () -> ServerHandler.getInstance().callback(new Command<>(Command.Operation.INSERT, category));
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void removeCategory(Category category) {
        Platform.runLater(() -> categories.remove(category));
        Runnable runCmd = () -> ServerHandler.getInstance().callback(new Command<>(Command.Operation.DELETE, category));
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void modifyCategory(Category category) {
        // nothing to do
    }

    @Override
    public ObservableList<Category> getCategories() {
        return categories;
    }

    @Override
    public void update() {
        Runnable runCmd = () -> {
            Command<List<Category>> command = ServerHandler.getInstance().callback(
                    new Command<>(Command.Operation.GET_ALL, Command.ObjectType.CATEGORY));
            if (command.getData() != null) {
                Platform.runLater(() -> categories.setAll(command.getData()));
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }
}