package at.fhhgb.caas.client.model.dao;

import at.fhhgb.caas.client.CaaSApp;
import at.fhhgb.caas.client.model.CommandExecutor;
import at.fhhgb.caas.client.model.DAOFactory;
import at.fhhgb.caas.data.Category;
import at.fhhgb.caas.model.dao.CategoryDAO;
import at.fhhgb.caas.model.dao.CategoryRMI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dinu Marius-Constantin on 27.05.2015.
 */
public class CategoryDAORmiImpl implements CategoryDAO {

    private static final Logger logger = Logger.getLogger(CaaSApp.CLIENT_LOGGER);

    private static final ObservableList<Category> categories = FXCollections.observableArrayList();

    private final CategoryRMI categoryRMI;

    public CategoryDAORmiImpl(CategoryRMI categoryRMI) {
        this.categoryRMI = categoryRMI;
    }

    @Override
    public void addCategory(Category category) {
        Runnable runCmd = () -> {
            try {
                categoryRMI.addCategory(category);
                Platform.runLater(() -> categories.add(category));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not add category!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void removeCategory(Category category) {
        Runnable runCmd = () -> {
            try {
                categoryRMI.removeCategory(category);
                Platform.runLater(() -> categories.remove(category));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not remove category!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public void modifyCategory(Category category) {
        Runnable runCmd = () -> {
            try {
                categoryRMI.modifyCategory(category);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not modify category!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }

    @Override
    public ObservableList<Category> getCategories() {
        return categories;
    }

    @Override
    public void update() {
        Runnable runCmd = () -> {
            try {
                List<Category> categories = categoryRMI.getCategories();
                Platform.runLater(() -> this.categories.setAll(categories));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not fetch category list!", e);
            }
        };
        CommandExecutor.offerCommand(runCmd);
    }
}
