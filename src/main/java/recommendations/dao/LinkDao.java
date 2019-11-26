package recommendations.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import recommendations.domain.Course;
import recommendations.domain.Link;
import recommendations.domain.Tag;

public class LinkDao implements ReaderDao<Link, String> {

    private Database database;

    public LinkDao(Database database) {
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
                    results.getString("metadata"), new ArrayList<Tag>(), new ArrayList<Course>(), results.getString("comment"));
            
            // Lisää ArrayListeihin vielä tägit ja kurssit!
            
            statement.close();
            results.close();
            connection.close();
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
                
                statement.close();
                
                ArrayList<Tag> tags = new ArrayList<>();
                
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tag JOIN BookTag ON BookTag.tag_id = Tag.id JOIN Book ON BookTag.book_id = ?");
                stmt.setInt(1, id);
                ResultSet tagResults = stmt.executeQuery();
                
                while (tagResults.next()) {
                    int tagId = tagResults.getInt("id");
                    String name = tagResults.getString("name");
                    tags.add(new Tag(tagId, name));
                }
                
                stmt.close();
                
                ArrayList<Course> courses = new ArrayList<>();
                
                PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM Tag JOIN CourseBook ON CourseBook.course_id = Course.id JOIN Book ON CourseBook.book_id = ?");
                stmt.setInt(1, id);
                ResultSet courseResults = stmt2.executeQuery();
                
                while (courseResults.next()) {
                    int courseId = tagResults.getInt("id");
                    String name = tagResults.getString("name");
                    courses.add(new Course(courseId, name));
                }
                
                stmt2.close();

                
                Link link = new Link(id, title, URL, type, metadata, tags, courses, comment);
                links.add(link);
            }
            connection.close();
        }

        return links;
    }

    @Override
    public boolean save(Link link) throws SQLException {

        if (!(this.findOne(link.getTitle()) == null)) {
            return false;
        }
        
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
            connection.close();
        }
        return true;
    }

    @Override
    public void delete(String title) throws Exception {
        try (Connection connection = database.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM Link WHERE title = ?")) {

            statement.setString(1, title);
            statement.executeUpdate();
            
            connection.close();
        }
    }

    @Override
    public boolean edit(Link link) throws SQLException {
        
        if (this.findOne(link.getTitle()) != null ) {
            return false;
        }
        
        try (Connection connection = database.getConnection()) {
            
            
            
            connection.close();
        }
        return true;
    }

}



