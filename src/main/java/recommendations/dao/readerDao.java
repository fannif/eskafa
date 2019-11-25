
package recommendations.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface readerDao<T, K> {
    
    T findOne(K title) throws SQLException;
    ArrayList<T> findAll() throws SQLException;
    boolean save(T tip) throws IOException;
    void delete(K title) throws Exception;
    
}
