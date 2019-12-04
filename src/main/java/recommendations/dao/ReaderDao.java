
package recommendations.dao;

import java.sql.SQLException;
import java.util.List;

public interface ReaderDao<T, K> {
    List<T> findByTag(K title) throws SQLException;
    List<T> findByCourse(K title) throws SQLException;
    T findOne(K title) throws SQLException;
    List<T> findAll() throws SQLException;
    boolean save(T tip) throws SQLException;
    void delete(K title) throws Exception;
    boolean edit(T tip) throws SQLException;
    List<T> findByWord(K word);
}
