package at.fhhgb.caas.server.model.dao;

import at.fhhgb.caas.data.Category;
import at.fhhgb.caas.data.Meal;
import at.fhhgb.caas.data.WeekDay;
import at.fhhgb.caas.model.dao.MealRMI;
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
 * Created by Dinu Marius-Constantin on 29.05.2015.
 */
public class MealDAODatabaseImpl implements MealRMI {

    private static final Logger logger = Logger.getLogger(CaaSServerApp.SERVER_LOGGER);

    private int getWeekDayId(WeekDay weekDay) throws SQLException {
        try (Statement statement = DAOFactory.getDatabaseConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(String.format("SELECT id FROM weekday WHERE name LIKE '%s';", weekDay.getName()))) {
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        return -1;
    }

    @Override
    public void addMeal(Meal meal) {
        if (meal != null) {
            try (Statement statement = DAOFactory.getDatabaseConnection().createStatement()) {
                for (Category c : meal.getCategories()) {
                    for (WeekDay w : meal.getAvailableDaySet()) {
                        statement.execute(String.format(
                                "INSERT INTO meal (name, category, weekday, price) VALUES ('%s', '%s', %d, %s);",
                                meal.getName(), c.getName(), getWeekDayId(w), meal.getPrice()));
                    }
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Could not execute statement!", e);
            }
        }
    }

    @Override
    public void filter(Category filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Meal> getMeals() {
        List<Meal> meals = new ArrayList<>();
        try (Statement statement = DAOFactory.getDatabaseConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT m.name, m.category, w.name AS weekday, m.price"
                             + "  FROM meal m, weekday w"
                             + "  WHERE m.weekday = w.id;")) {
            while (resultSet.next()) {
                Meal m = new Meal(resultSet.getString("name"));
                m.setPrice(resultSet.getDouble("price"));
                if (meals.contains(m)) {
                    meals.get(meals.indexOf(m)).addCategory(new Category(resultSet.getString("category")));
                    meals.get(meals.indexOf(m)).addAvailableDay(WeekDay.getType(resultSet.getString("weekday")));
                } else {
                    meals.add(m);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Could not execute statement!", e);
        }
        return meals;
    }

    @Override
    public void removeMeal(Meal meal) {
        if (meal != null) {
            try (Statement statement = DAOFactory.getDatabaseConnection().createStatement()) {
                statement.execute(String.format("DELETE FROM meal WHERE name LIKE '%s';", meal.getName()));
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Could not execute statement!", e);
            }
        }
    }

    @Override
    public void modifyMeal(Meal meal) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException();
    }
}
