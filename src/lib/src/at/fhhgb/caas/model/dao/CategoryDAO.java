package at.fhhgb.caas.model.dao;

import at.fhhgb.caas.data.Category;

import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 13.05.2015.
 */
public interface CategoryDAO extends DataUpdateDAO {

    void addCategory(Category category) throws Exception;

    void removeCategory(Category category) throws Exception;

    void modifyCategory(Category category) throws Exception;

    List<Category> getCategories() throws Exception;

}
