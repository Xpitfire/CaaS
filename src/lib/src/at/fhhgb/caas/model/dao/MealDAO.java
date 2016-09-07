package at.fhhgb.caas.model.dao;

import at.fhhgb.caas.data.Category;
import at.fhhgb.caas.data.Meal;

import java.rmi.Remote;
import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 13.05.2015.
 */
public interface MealDAO extends DataUpdateDAO, Remote {

    void addMeal(Meal meal) throws Exception;

    void filter(Category filter) throws Exception;

    List<Meal> getMeals() throws Exception;

    void removeMeal(Meal meal) throws Exception;

    void modifyMeal(Meal meal) throws Exception;
}
