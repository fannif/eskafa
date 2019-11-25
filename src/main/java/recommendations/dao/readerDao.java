
package recommendations.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface readerDao<T, K> {
    
    T findOne(K title) throws SQLException;
    List<T> findAll() throws SQLException;
    void save(T tip) throws SQLException;
    void delete(K title) throws Exception;
    
}
