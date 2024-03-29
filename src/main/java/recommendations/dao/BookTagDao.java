
package recommendations.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class BookTagDao {
    
    private Database database;
    
    public BookTagDao(Database database) {
        this.database = database;
    }
    
    public void save(int bookId, int tagId) {
        
        try (Connection connection = database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO BookTag(book_id, tag_id) VALUES (?, ?)")) {
                statement.setInt(1, bookId);
                statement.setInt(2, tagId);
                
                statement.executeUpdate();
                statement.close();
            }
            connection.close();
        } catch (Exception e) {
            
        }
    }
    
    public void deleteByBook(int id) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM BookTag "
                    + "WHERE book_id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
    }
    
}
