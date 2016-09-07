package at.fhhgb.caas.server.model.dao;

import at.fhhgb.caas.data.Category;
import at.fhhgb.caas.model.dao.CategoryRMI;
import at.fhhgb.caas.server.CaaSServerApp;
import at.fhhgb.caas.server.model.DAOFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dinu Marius-Constantin on 27.05.2015.
 */
public class CategoryDAODatabaseImpl implements CategoryRMI {

    private static final Logger logger = Logger.getLogger(CaaSServerApp.SERVER_LOGGER);

    @Override
    public void addCategory(Category category) {
        if (category != null) {
            try (Statement statement = DAOFactory.getDatabaseConnection().createStatement()) {
                statement.execute(String.format("INSERT INTO category (name) VALUES ('%s');", category.getName()));
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Could not execute statement!", e);
            }
        }
    }

    @Override
    public void removeCategory(Category category) {
        if (category != null) {
            try (Statement statement = DAOFactory.getDatabaseConnection().createStatement()) {
                statement.execute(String.format("DELETE FROM category WHERE name = '%s';", category.getName()));
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Could not execute statement!", e);
            }
        }
    }

    @Override
    public void modifyCategory(Category category) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        try (Statement statement = DAOFactory.getDatabaseConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM category;")) {
            while (resultSet.next())
                categories.add(new Category(resultSet.getString("name")));
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Could not execute statement!", e);
        }
        return categories;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException();
    }
}
