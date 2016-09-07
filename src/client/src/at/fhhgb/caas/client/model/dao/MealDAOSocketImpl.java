package at.fhhgb.caas.client.model.dao;

import at.fhhgb.caas.client.model.CommandExecutor;
import at.fhhgb.caas.client.socket.ServerHandler;
import at.fhhgb.caas.data.Category;
import at.fhhgb.caas.data.Command;
import at.fhhgb.caas.data.Meal;
import at.fhhgb.caas.model.dao.DataUpdateDAO;import at.fhhgb.caas.model.dao.MealDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dinu Marius-Constantin on 13.05.2015.
 */
public class MealDAOSocketImpl implements MealDAO {

    private static final ObservableList<Meal> mealList = FXCollections.observableArrayList();
    private static final ObservableList<Meal> viewList = FXCollections.observableArrayList();

    private Category curFilter;

    @Override
    public void addMeal(Meal meal) {
        if (mealList.contains(meal))
            return;
        Platform.runLater(() -> {
            mealList.add(meal);
            filter(curFilter);
        });
        Runnable runCmd = () -> ServerHandler.getInstance().callback(new Command<>(Command.Operation.INSERT, meal));
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void filter(Category filter) {
        this.curFilter = filter;
        if (filter == null) {
            Platform.runLater(() -> viewList.setAll(mealList));
        } else {
            Platform.runLater(() -> {
                viewList.clear();
                mealList.stream()
                    .filter(m -> m.getCategories().stream().anyMatch(c -> c.equals(filter)))
                    .forEach(m -> viewList.add(m));
            });
        }
    }

    @Override
    public ObservableList<Meal> getMeals() {
        return viewList;
    }

    @Override
    public void removeMeal(Meal meal) {
        Platform.runLater(() -> {
            mealList.remove(meal);
            filter(curFilter);
        });
        Runnable runCmd = () -> ServerHandler.getInstance().callback(new Command<>(Command.Operation.DELETE, meal));
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void modifyMeal(Meal meal) {
        Runnable runCmd = () -> ServerHandler.getInstance().callback(new Command<>(Command.Operation.MODIFY, meal));
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void update() {
        Runnable runCmd = () -> {
                Command<List<Meal>> command = ServerHandler.getInstance().callback(
                        new Command<>(Command.Operation.GET_ALL, Command.ObjectType.MEAL));
            if (command.getData() != null) {
                Platform.runLater(() -> {
                    mealList.setAll(command.getData());
                    filter(curFilter);
                });
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }
}