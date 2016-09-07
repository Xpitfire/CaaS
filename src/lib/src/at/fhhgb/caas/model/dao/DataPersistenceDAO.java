package at.fhhgb.caas.model.dao;

import java.util.List;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public interface DataPersistenceDAO<T> {

    String getFileName();

    void initData(List<T> data);

}
