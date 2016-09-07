package at.fhhgb.caas.server.test;

import at.fhhgb.caas.crypto.DataCrypto;
import at.fhhgb.caas.data.*;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public class SerializationTest {

    @Test
    public void testSerializeCategory() throws IOException, ClassNotFoundException {
        List<Category> categories = new ArrayList<>();
        List<Category> recallList;
        Category tmp1 = new Category("Vegetarian");
        categories.add(tmp1);
        tmp1 = new Category("Fried & Grilled");
        categories.add(tmp1);
        tmp1 = new Category("Fish");
        categories.add(tmp1);

        String fileName = "category-data.ser";

        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(categories);
        out.close();

        ObjectInput in = new ObjectInputStream(new FileInputStream(fileName));
        recallList = (List<Category>) in.readObject();
        in.close();

        assertEquals(categories, recallList);
    }

    @Test
    public void testSerializePerson() throws IOException, ClassNotFoundException {
        List<Person> persons = new ArrayList<>();
        List<Person> recallList;
        Person tmp1 = new Person("admin", "s13847483", DataCrypto.encrypt("12"), AuthType.ADMIN);
        tmp1.setFirstName("Marius");
        tmp1.setLastName("Dinu");
        tmp1.setEmail("test@mail.com");
        tmp1.setProfilePicturePath("/pics/my-img.png");
        tmp1.setUserStatus(UserStatus.ACTIVE);
        persons.add(tmp1);

        String fileName = "person-data.ser";

        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(persons);
        out.close();

        ObjectInput in = new ObjectInputStream(new FileInputStream(fileName));
        recallList = (List<Person>) in.readObject();
        in.close();

        assertEquals(persons, recallList);
    }

    @Test
    public void testSerializeMeal() throws IOException, ClassNotFoundException {
        List<Meal> meals = new ArrayList<>();
        List<Meal> recallList;
        Meal tmp1 = new Meal("Campina Burger");
        tmp1.setPrice(8.43);
        tmp1.addAvailableDay(WeekDay.MONDAY);
        tmp1.addAvailableDay(WeekDay.WEDNESDAY);
        tmp1.addCategory(new Category("Fried & Grilled"));
        meals.add(tmp1);

        String fileName = "meal-data.ser";

        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(meals);
        out.close();

        ObjectInput in = new ObjectInputStream(new FileInputStream(fileName));
        recallList = (List<Meal>) in.readObject();
        in.close();

        assertEquals(meals, recallList);
    }

    @Test
    public void testSerializeOrder() throws IOException, ClassNotFoundException {
        List<Order> orders = new ArrayList<>();
        List<Order> recallList;
        Order tmp1 = new Order("s12145543", "001");
        tmp1.setDeliverHour("12:05");
        tmp1.setOrderStatus(OrderStatus.OPEN);
        Meal m = new Meal("Campina Burger");
        m.setPrice(8.43);
        m.addAvailableDay(WeekDay.FRIDAY);
        m.addCategory(new Category("Fried & Grilled"));
        tmp1.addMeal(m);
        orders.add(tmp1);

        String fileName = "order-data.ser";

        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(orders);
        out.close();

        ObjectInput in = new ObjectInputStream(new FileInputStream(fileName));
        recallList = (List<Order>) in.readObject();
        in.close();

        assertEquals(orders, recallList);
    }

}
