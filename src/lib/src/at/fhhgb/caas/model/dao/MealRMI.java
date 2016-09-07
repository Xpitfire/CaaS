package at.fhhgb.caas.model.dao;

import at.fhhgb.caas.data.Category;
import at.fhhgb.caas.data.Meal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 29.05.2015.
 */
public interface MealRMI extends MealDAO, DataUpdateRMI, Remote {

    @Override
    void addMeal(Meal meal) throws RemoteException;

    @Override
    void filter(Category filter) throws RemoteException;

    @Override
    List<Meal> getMeals() throws RemoteException;

    @Override
    void removeMeal(Meal meal) throws RemoteException;

    @Override
    void modifyMeal(Meal meal) throws RemoteException;

}
