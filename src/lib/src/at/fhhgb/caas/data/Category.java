package at.fhhgb.caas.data;

import java.io.Serializable;

/**
 * Created by Dinu Marius-Constantin on 13.05.2015.
 */
public class Category implements Serializable, Comparable<Category> {

    private static final long serialVersionUID = -5286629936766227961L;

    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;

        return !(name != null ? !name.equals(category.name) : category.name != null);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public int compareTo(Category o) {
        return name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return name;
    }

}
