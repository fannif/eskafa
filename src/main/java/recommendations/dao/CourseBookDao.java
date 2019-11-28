
package recommendations.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class CourseBookDao {
    
    private Database database;
    
    public CourseBookDao(Database database) {
        this.database = database;
    }
    
    public void save(int bookId, int courseId) {
        
        try (Connection connection = database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO CourseBook(book_id, course_id) VALUES (?, ?)")) {
                statement.setInt(1, bookId);
                statement.setInt(2, courseId);
                
                statement.executeUpdate();
                statement.close();
            }
            connection.close();
        } catch (Exception e) {
            
        }
    }
    
}
