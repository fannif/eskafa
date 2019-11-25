

package recommendations.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import recommendations.domain.Link;

public class linkDao implements readerDao<Link, String> {
    
    private Database database;
    
    public linkDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Link findOne(String title) throws SQLException {
        Link link;
        try (Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Link WHERE title = ?");
            statement.setString(1, title);
            ResultSet results = statement.executeQuery();
            boolean hasOne = results.next();
            if (!hasOne) {
                return null;
            }   link = new Link(results.getInt("id"), results.getString("title"),
                    results.getString("URL"), results.getString("type"),
                    results.getString("metadata"), new ArrayList<String>(), new ArrayList<String>(), results.getString("comment"));
            // Lisää ArrayListeihin vielä tägit ja kurssit!
            statement.close();
            results.close();
        }

        return link;
    }
    
    @Override
    public List<Link> findAll() throws SQLException {
        
        List<Link> links;
        try (Connection connection = database.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM Link");
            links = new ArrayList<>();
            while (results.next()) {
                int id = results.getInt("id");
                String title = results.getString("title");
                String URL = results.getString("URL");
                String type = results.getString("type");
                String metadata = results.getString("metadata");
                String comment = results.getString("comment");
                
                // Lisää tägien ja kurssien näyttäminen!
                
                Link link = new Link(id, title, URL, type, metadata, new ArrayList<String>(), new ArrayList<String>(), comment);
            }
        }
        
        return links;
    }
    
    @Override
    public void save(Link link) throws SQLException {
        
        try (Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Link(title, URL, type, metadata, comment)"
                    + " VALUES ( ? , ? , ? , ? , ?)");
            statement.setString(1, link.getTitle());
            statement.setString(2, link.getURL());
            statement.setString(3, link.getType());
            statement.setString(4, link.getMetadata());
            statement.setString(5, link.getComment());
            // Lisää vielä tagien ja kurssien lisäys!
            statement.executeUpdate();
        }
    }
    
    @Override
    public void delete(String title) throws Exception {
        try (Connection connection = database.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM Link WHERE title = ?")) {
            
            statement.setString(1, title);
            statement.executeUpdate();
            
        }
    }
    
    @Override
    public void edit(Link link) {
        
    }
    
}

