package at.fhhgb.caas.server.model.dao;

import at.fhhgb.caas.data.Category;
import at.fhhgb.caas.model.dao.CategoryDAO;
import at.fhhgb.caas.model.dao.DataPersistenceDAO;
import at.fhhgb.caas.server.model.DAOFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public class CategoryDAOFileImpl implements CategoryDAO, DataPersistenceDAO<Category> {

    private static List<Category> categories = new ArrayList<>();

    private static final String fileName = "category-data";

    @Override
    public void addCategory(Category category) {
        synchronized (DAOFactory.SYNC_OBJECT) {
            if (!categories.contains(category))
                categories.add(category);
        }
    }

    @Override
    public void removeCategory(Category category) {
        synchronized (DAOFactory.SYNC_OBJECT) {
            categories.remove(category);
        }
    }

    @Override
    public void modifyCategory(Category category) {
        // nothing to do
    }

    @Override
    public List<Category> getCategories() {
        synchronized (DAOFactory.SYNC_OBJECT) {
            return categories;
        }
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void initData(List<Category> data) {
        synchronized (DAOFactory.SYNC_OBJECT) {
            categories = data;
        }
    }

}
