package at.fhhgb.caas.data;

import java.io.Serializable;

/**
 * Created by Dinu Marius-Constantin on 11.05.2015.
 */
public class Person implements Serializable, Comparable<Person> {

    private static final long serialVersionUID = -5198528629234517961L;

    private String username;
    private CharSequence password;

    private String id;

    private AuthType authType;
    private UserStatus userStatus;

    private String firstName;
    private String lastName;

    private String profilePicturePath;

    private String email;

    public Person() {
    }

    public Person(String username) {
        this(username, null, null, null);
    }

    public Person(String username, String id, CharSequence password, AuthType authType) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.authType = authType;
        this.userStatus = UserStatus.ACTIVE;
    }

    public String getUsername() {
        return username;
    }

    public CharSequence getPassword() {
        return password;
    }

    public void setPassword(CharSequence password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicture) {
        this.profilePicturePath = profilePicture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!username.equals(person.username)) return false;
        if (password != null ? !password.equals(person.password) : person.password != null) return false;
        if (!id.equals(person.id)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + id.hashCode();
        result = 31 * result + (authType != null ? authType.hashCode() : 0);
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public int compareTo(Person o) {
        return username.compareTo(o.username);
    }

}
