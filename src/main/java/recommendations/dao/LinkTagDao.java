
package recommendations.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class LinkTagDao {
    
    private Database database;
    
    public LinkTagDao(Database database) {
        this.database = database;
    }
    
    public void save(int linkId, int tagId) {
        
        try (Connection connection = database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO LinkTag(link_id, tag_id) VALUES (?, ?)")) {
                statement.setInt(1, linkId);
                statement.setInt(2, tagId);
                
                statement.executeUpdate();
                statement.close();
            }
            connection.close();
        } catch (Exception e) {
            
        }
    }
    
    public void deleteByLink(int id) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM LinkTag "
                    + "WHERE link_id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
    }
    
}
