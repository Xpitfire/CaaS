package at.fhhgb.caas.server.test;

import at.fhhgb.caas.data.Category;
import at.fhhgb.caas.data.Meal;
import at.fhhgb.caas.data.Order;
import at.fhhgb.caas.data.WeekDay;
import at.fhhgb.caas.server.model.DAOFactory;
/**
 * Created by Dinu Marius-Constantin on 03.06.2015.
 */
public class AddDataToDatabase {

    public static void main(String[] args) throws Exception {
        DAOFactory.createInstance();

        Order o = new Order("s1310307054");
        o.setDeliverHour("12:00");
        Category c = new Category("Fried & Grilled");
        DAOFactory.getCategoryDAO().addCategory(c);

        Meal m = new Meal("Campina Burger");
        m.setPrice(7.50);
        m.addAvailableDay(WeekDay.FRIDAY);
        m.addCategory(c);
        DAOFactory.getMealDAO().addMeal(m);

        o.addMeal(m);
        DAOFactory.getOrderDAO().addOrder(o);

        System.exit(0);
    }

}
