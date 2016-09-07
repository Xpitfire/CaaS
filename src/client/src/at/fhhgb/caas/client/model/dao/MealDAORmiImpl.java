package at.fhhgb.caas.client.model.dao;

import at.fhhgb.caas.client.CaaSApp;
import at.fhhgb.caas.client.model.CommandExecutor;
import at.fhhgb.caas.client.model.DAOFactory;
import at.fhhgb.caas.data.Category;
import at.fhhgb.caas.data.Meal;
import at.fhhgb.caas.model.dao.MealDAO;
import at.fhhgb.caas.model.dao.MealRMI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dinu Marius-Constantin on 29.05.2015.
 */
public class MealDAORmiImpl implements MealDAO {

    private static final Logger logger = Logger.getLogger(CaaSApp.CLIENT_LOGGER);

    private static final ObservableList<Meal> mealList = FXCollections.observableArrayList();
    private static final ObservableList<Meal> viewList = FXCollections.observableArrayList();

    private Category curFilter;

    private final MealRMI mealRMI;

    public MealDAORmiImpl(MealRMI mealRMI) {
        this.mealRMI = mealRMI;
    }

    @Override
    public void addMeal(Meal meal) throws Exception {
        Runnable runCmd = () -> {
            try {
                mealRMI.addMeal(meal);
                Platform.runLater(() -> mealList.add(meal));
                filter(curFilter);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not add meal!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void filter(Category filter) throws Exception {
        Runnable runCmd = () -> {
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
        };
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public List<Meal> getMeals() throws Exception {
        return viewList;
    }

    @Override
    public void removeMeal(Meal meal) throws Exception {
        Runnable runCmd = () -> {
            try {
                mealRMI.removeMeal(meal);
                Platform.runLater(() -> mealList.remove(meal));
                filter(curFilter);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not remove meal!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void modifyMeal(Meal meal) throws Exception {
        Runnable runCmd = () -> {
            try {
                mealRMI.modifyMeal(meal);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not modify meal!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void update() throws Exception {
        Runnable runCmd = () -> {
            try {
                List<Meal> meals = mealRMI.getMeals();
                Platform.runLater(() -> mealList.setAll(meals));
                filter(curFilter);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not fetch meal list!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }
}
