
package recommendations.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class CourseLinkDao {
    
    private Database database;
    
    public CourseLinkDao(Database database) {
        this.database = database;
    }
    
    public void save(int linkId, int courseId) {
        
        try (Connection connection = database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO CourseLink(link_id, course_id) VALUES (?, ?)")) {
                statement.setInt(1, linkId);
                statement.setInt(2, courseId);
                
                statement.executeUpdate();
                statement.close();
            }
            connection.close();
        } catch (Exception e) {
            
        }
    }
    
    public void deleteByLink(int id) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM CourseLink "
                    + "WHERE link_id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
    }
    
}
