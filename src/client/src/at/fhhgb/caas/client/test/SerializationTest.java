package at.fhhgb.caas.client.test;

import at.fhhgb.caas.client.socket.ServerHandler;
import at.fhhgb.caas.data.*;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public class SerializationTest {

    @Test
    public void testSerializeCategory() throws IOException, ClassNotFoundException {
        Category tmp1 = new Category("Vegetarian"), tmp2;
        String fileName = "categoryTest.ser";

        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(tmp1);
        out.close();

        ObjectInput in = new ObjectInputStream(new FileInputStream(fileName));
        tmp2 = (Category) in.readObject();
        in.close();

        assertEquals(tmp1, tmp2);
    }

    @Test
    public void testSerializePerson() throws IOException, ClassNotFoundException {
        Person tmp1 = new Person("mark", "12345", "test", AuthType.ADMIN), tmp2;
        tmp1.setFirstName("Hans");
        tmp1.setLastName("Schultz");
        tmp1.setEmail("test@mail.com");
        tmp1.setProfilePicturePath("/pics/my-img.png");
        tmp1.setUserStatus(UserStatus.BLOCKED);

        String fileName = "personTest.ser";

        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(tmp1);
        out.close();

        ObjectInput in = new ObjectInputStream(new FileInputStream(fileName));
        tmp2 = (Person) in.readObject();
        in.close();

        assertEquals(tmp1, tmp2);
    }

    @Test
    public void testSerializeMeal() throws IOException, ClassNotFoundException {
        Meal tmp1 = new Meal("Campina Burger"), tmp2;
        tmp1.setPrice(8.43);
        tmp1.addAvailableDay(WeekDay.MONDAY);
        tmp1.addAvailableDay(WeekDay.WEDNESDAY);
        tmp1.addCategory(new Category("Fried & Grilled"));
        tmp1.addCategory(new Category("Happy Meal"));
        String fileName = "mealTest.ser";

        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(tmp1);
        out.close();

        ObjectInput in = new ObjectInputStream(new FileInputStream(fileName));
        tmp2 = (Meal) in.readObject();
        in.close();

        assertEquals(tmp1, tmp2);
    }

    @Test
    public void testServerWrite() {
        Person tmp1 = new Person("mark", "12345", "test", AuthType.ADMIN);
        tmp1.setFirstName("Hans");
        tmp1.setLastName("Schultz");
        tmp1.setEmail("test@mail.com");
        tmp1.setProfilePicturePath("/pics/my-img.png");
        tmp1.setUserStatus(UserStatus.BLOCKED);

        ServerHandler serverHandler = ServerHandler.getInstance();
        serverHandler.callback(new Command<>(Command.Operation.INSERT, tmp1));

        serverHandler.close();
    }

}
