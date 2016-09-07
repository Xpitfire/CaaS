package at.fhhgb.caas.data;

import java.io.Serializable;

/**
 * Created by Dinu Marius-Constantin on 14.05.2015.
 */
public enum OrderStatus implements Serializable {

    OPEN("OPEN"),
    PROCESSED("PROCESSED"),
    FINISHED("FINISHED"),
    CANCELED("CANCELED"),
    ;

    private static final long serialVersionUID = -2919852869989975961L;

    private String name;

    OrderStatus() {
    }

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static OrderStatus getType(String orderStatus) {
        switch (orderStatus) {
            case "OPEN":
                return OPEN;
            case "PROCESSED":
                return PROCESSED;
            case "FINISHED":
                return FINISHED;
            case "CANCELED":
                return CANCELED;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
