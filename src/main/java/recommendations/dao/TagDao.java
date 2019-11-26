
package recommendations.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import recommendations.domain.Tag;

public class TagDao implements ReaderDao<Tag, String> {

    private Database database;

    public TagDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Tag findOne(String title) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tag> findAll() throws SQLException {
        List<Tag> tags;
        try (Connection connection = database.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM Tag");
            tags = new ArrayList<>();
            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");

                Tag tag = new Tag(id, name);
                tags.add(tag);
            }
            connection.close();
        }

        return tags;
    }

    @Override
    public boolean save(Tag tip) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String title) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean edit(Tag tip) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
