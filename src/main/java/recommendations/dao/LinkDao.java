

package recommendations.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import recommendations.domain.Link;

public class LinkDao implements readerDao<Link, String> {
    
    private Database database;
    
    public LinkDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Link findOne(String title) throws SQLException {
        Link link;
        ArrayList<String> linkRelatedTags = new ArrayList<>();
        ArrayList<String> linkRelatedCourses = new ArrayList<>();
        try (Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Link WHERE title = ?");
            statement.setString(1, title);
            ResultSet results = statement.executeQuery();
            boolean hasOne = results.next();
            if (!hasOne) {
                return null;
            }   link = new Link(results.getInt("id"), results.getString("title"),
                    results.getString("URL"), results.getString("type"),
                    results.getString("metadata"), linkRelatedTags, linkRelatedCourses, results.getString("comment"));
            statement.close();
            results.close();
        }
        link.setTags(getLinkRelatedTags(linkRelatedTags));
        link.setCourses(getLinkRelatedCourses(linkRelatedCourses));
        return link;
    }

    private ArrayList<String> getLinkRelatedTags(ArrayList<String> courseRelatedTags) throws SQLException {
        String sql = "SELECT * FROM Tag JOIN Link ON Tag.link_id = Link.id";
        try (Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                courseRelatedTags.add(results.getString("name"));
            }

            statement.close();
            results.close();
        }
        return courseRelatedTags;
    }

    private ArrayList<String> getLinkRelatedCourses(ArrayList<String> linkRelatedCourses)  throws SQLException {
        String sql = "SELECT * FROM Course JOIN Link ON Course.link_id = Link.id";
        try (Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                linkRelatedCourses.add(results.getString("name"));
            }

            statement.close();
            results.close();
        }
        return linkRelatedCourses;
    }

    @Override
    public List<Link> findAll() throws SQLException {
        ArrayList<String> linkRelatedTags = new ArrayList<>();
        ArrayList<String> linkRelatedCourses = new ArrayList<>();
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
                ResultSet tags = statement.executeQuery("SELECT * FROM Tag JOIN Link ON Tag.link_id = Link.id ");
                while (results.next()) {
                    linkRelatedTags.add(tags.getString("name"));
                }
                tags.close();
                ResultSet courses = statement.executeQuery("SELECT * FROM Course JOIN Link ON Course.link_id = Link.id");
                while(courses.next()) {
                    linkRelatedCourses.add(courses.getString("name"));
                }
                Link link = new Link(id, title, URL, type, metadata, linkRelatedTags, linkRelatedCourses, comment);
                links.add(link);
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
            statement.setString(7, link.getComment());
            // Lisää vielä tagien ja kurssien lisäys!
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

