package at.fhhgb.caas.model.dao;

import at.fhhgb.caas.data.AuthType;
import at.fhhgb.caas.data.Person;

import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 12.05.2015.
 */
public interface PersonDAO extends DataUpdateDAO {

    AuthType authenticateUserInput(String username, CharSequence password) throws Exception;

    Person getPerson(String username) throws Exception;

    boolean contains(String username) throws Exception;

    boolean addPerson(Person person) throws Exception;

    List<Person> getPersons() throws Exception;

    void removePerson(Person person) throws Exception;

    void modifyPerson(Person person) throws Exception;

}
