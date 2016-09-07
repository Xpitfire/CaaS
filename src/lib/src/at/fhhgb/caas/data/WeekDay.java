package at.fhhgb.caas.data;

import java.io.Serializable;

/**
 * Created by Dinu Marius-Constantin on 13.05.2015.
 */
public enum WeekDay implements Serializable {

    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday")
    ;

    private static final long serialVersionUID = -1986551822224347961L;

    private String name;

    WeekDay() {
    }

    WeekDay(String name) {
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

    public static WeekDay getType(String weekday) {
        switch (weekday) {
            case "Monday":
                return MONDAY;
            case "Tuesday":
                return TUESDAY;
            case "Wednesday":
                return WEDNESDAY;
            case "Thursday":
                return THURSDAY;
            case "Friday":
                return FRIDAY;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
