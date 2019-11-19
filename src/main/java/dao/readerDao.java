
package dao;

import java.io.IOException;
import java.util.ArrayList;

public interface readerDao<T, K> {
    
    T findOne(K title);
    ArrayList<T> findAll();
    boolean save(T tip) throws IOException;
    void delete(K title) throws Exception;
    
}
