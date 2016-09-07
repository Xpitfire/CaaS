package at.fhhgb.caas.data;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Dinu Marius-Constantin on 13.05.2015.
 */
public class Meal implements Serializable, Comparable<Meal> {

    private static final long serialVersionUID = -6765332356993627961L;

    private Set<Category> categories = new TreeSet<>();
    private Set<WeekDay> availableDaySet = new TreeSet<>();

    private String name;
    private double price;

    public Meal() {
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void addAvailableDay(WeekDay weekDay) {
        availableDaySet.add(weekDay);
    }

    public String getName() {
        return name;
    }

    public Meal(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<WeekDay> getAvailableDaySet() {
        return availableDaySet;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void setAvailableDaySet(Set<WeekDay> availableDaySet) {
        this.availableDaySet = availableDaySet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meal meal = (Meal) o;

        if (Double.compare(meal.price, price) != 0) return false;
        return name.equals(meal.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = categories != null ? categories.hashCode() : 0;
        result = 31 * result + (availableDaySet != null ? availableDaySet.hashCode() : 0);
        result = 31 * result + name.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public int compareTo(Meal o) {
        return name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return name;
    }

}
