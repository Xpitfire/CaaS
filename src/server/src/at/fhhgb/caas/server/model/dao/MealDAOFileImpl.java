package at.fhhgb.caas.server.model.dao;

import at.fhhgb.caas.data.Category;
import at.fhhgb.caas.data.Meal;
import at.fhhgb.caas.model.dao.DataPersistenceDAO;
import at.fhhgb.caas.model.dao.MealDAO;
import at.fhhgb.caas.server.model.DAOFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public class MealDAOFileImpl implements MealDAO, DataPersistenceDAO<Meal> {

    private static List<Meal> mealList = new ArrayList<>();

    private static final String fileName = "meal-data";

    @Override
    public void addMeal(Meal meal) {
        synchronized (DAOFactory.SYNC_OBJECT) {
            if (!mealList.contains(meal))
                mealList.add(meal);
        }
    }

    @Override
    public void filter(Category filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Meal> getMeals() {
        synchronized (DAOFactory.SYNC_OBJECT) {
            return mealList;
        }
    }

    @Override
    public void removeMeal(Meal meal) {
        synchronized (DAOFactory.SYNC_OBJECT) {
            mealList.remove(meal);
        }
    }

    @Override
    public void modifyMeal(Meal meal) {
        if (meal == null)
            return;
        synchronized (DAOFactory.SYNC_OBJECT) {
            mealList.stream()
                    .filter(m -> m.getName().equals(meal.getName()))
                    .findFirst()
                    .ifPresent(m -> {
                        m.setPrice(meal.getPrice());
                        m.setAvailableDaySet(meal.getAvailableDaySet());
                        m.setCategories(meal.getCategories());
                    });
        }
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void initData(List<Meal> data) {
        synchronized (DAOFactory.SYNC_OBJECT) {
            mealList = data;
        }
    }

}
