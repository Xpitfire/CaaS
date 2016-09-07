package at.fhhgb.caas.data;

import java.io.Serializable;

/**
 * Created by Dinu Marius-Constantin on 12.05.2015.
 */
public enum AuthType implements Serializable {
    USER("User"),
    ADMIN("Admin"),
    STAFF("Staff");

    private static final long serialVersionUID = -6866529936726229671L;

    private String name;

    AuthType() {
    }

    AuthType(String name) {
        this.name = name;
    }

    public static AuthType getType(String name) {
        switch (name) {
            case "User":
                return USER;
            case "Admin":
                return ADMIN;
            case "Staff":
                return STAFF;
            default:
                throw new  UnsupportedOperationException();
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
