package at.fhhgb.caas.server.test;

import at.fhhgb.caas.data.*;
import at.fhhgb.caas.server.model.DAOFactory;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by Dinu Marius-Constantin on 03.06.2015.
 */
public class DatabaseTest {

    static {
        DAOFactory.createInstance();
    }

    @Test
    public void testCategoryDAODatabaseImpl() throws Exception {
        Category c = new Category("Fish");
        DAOFactory.getCategoryDAO().addCategory(c);
        assertTrue(DAOFactory.getCategoryDAO().getCategories().contains(c));

        DAOFactory.getCategoryDAO().removeCategory(c);
        assertTrue(!DAOFactory.getCategoryDAO().getCategories().contains(c));
    }

    @Test
    public void testMealDAODatabaseImpl() throws Exception {
        Category c = new Category("Fish");
        DAOFactory.getCategoryDAO().addCategory(c);

        Meal m = new Meal("Fish Burger");
        m.setPrice(5.0);
        m.addAvailableDay(WeekDay.FRIDAY);
        m.addCategory(c);

        DAOFactory.getMealDAO().addMeal(m);
        assertTrue(DAOFactory.getMealDAO().getMeals().contains(m));

        DAOFactory.getMealDAO().removeMeal(m);
        assertTrue(!DAOFactory.getMealDAO().getMeals().contains(m));

        DAOFactory.getCategoryDAO().removeCategory(c);
    }

    @Test
    public void testPersonDAODatabaseImpl() throws Exception {
        Person p = new Person("test", "s5555555", "password", AuthType.USER);

        DAOFactory.getPersonDAO().addPerson(p);
        assertTrue(DAOFactory.getPersonDAO().getPersons().contains(p));

        DAOFactory.getPersonDAO().removePerson(p);
        assertTrue(!DAOFactory.getPersonDAO().getPersons().contains(p));
    }

    @Test
    public void testOrderDAODatabaseImpl() throws Exception {
        Order o = new Order("s1310307054", "1");
        o.setDeliverHour("12:00");
        Category c = new Category("Fried & Grilled");
        DAOFactory.getCategoryDAO().addCategory(c);

        Meal m = new Meal("Campina Burger");
        m.setPrice(7.50);
        m.addAvailableDay(WeekDay.MONDAY);
        m.addAvailableDay(WeekDay.FRIDAY);
        m.addCategory(c);
        DAOFactory.getMealDAO().addMeal(m);
        o.addMeal(m);
        DAOFactory.getOrderDAO().addOrder(o);
        assertTrue(DAOFactory.getOrderDAO().getOrders().contains(o));

        DAOFactory.getOrderDAO().removeOrder(o);
        DAOFactory.getMealDAO().removeMeal(m);
        DAOFactory.getCategoryDAO().removeCategory(c);
        assertTrue(!DAOFactory.getOrderDAO().getOrders().contains(o));
    }

}
