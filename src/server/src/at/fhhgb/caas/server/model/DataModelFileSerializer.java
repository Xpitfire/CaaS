package at.fhhgb.caas.server.model;

import java.io.*;

/**
 * Created by Dinu Marius-Constantin on 19.05.2015.
 */
public final class DataModelFileSerializer {

    public static final String FILE_EXTENSION = ".ser";

    public static void writeFile(Object collection, String fileName) {
        if (fileName == null || fileName.length() <= 0)
            return;
        try (ObjectOutput out = new ObjectOutputStream(
                new FileOutputStream(String.format("%s%s", fileName, FILE_EXTENSION)))) {
            out.writeObject(collection);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T readFile(String fileName) {
        if (fileName == null || fileName.length() <= 0)
            return null;
        try (ObjectInput in = new ObjectInputStream(
                new FileInputStream(String.format("%s%s", fileName, FILE_EXTENSION)))) {
            return  (T) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
