package at.fhhgb.caas.data;

import java.io.Serializable;

/**
 * Created by Dinu Marius-Constantin on 14.05.2015.
 */
public enum UserStatus implements Serializable {

    ACTIVE("Active"),
    BLOCKED("Blocked")
    ;

    private static final long serialVersionUID = -8651982222434517961L;

    private String name;

    UserStatus() {
    }

    UserStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static UserStatus getType(String value) {
        switch (value) {
            case "Active":
                return ACTIVE;
            case "Blocked":
                return BLOCKED;
            default:
                throw new  UnsupportedOperationException();
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
