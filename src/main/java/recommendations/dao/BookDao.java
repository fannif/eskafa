
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
import recommendations.domain.Book;
import recommendations.domain.Course;
import recommendations.domain.Tag;

public class BookDao implements ReaderDao<Book, String> {
    
    private Database database;
    
    public BookDao(Database database) {
        this.database = database;
    }
    
    public ArrayList<Book> findByTag(String tag) {
        ArrayList<Book> books = new ArrayList<>();
        
        try {
            for (Book book: this.findAll()) {
                for (Tag bookTag: book.getTags()) {
                    if (bookTag.getName().equals(tag)) {
                        books.add(book);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LinkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return books;
    }
    
    @Override
    public Book findOne(String title) throws SQLException {
        Book book;
        try (Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Book WHERE title = ?");
            statement.setString(1, title);
            ResultSet results = statement.executeQuery();
            boolean hasOne = results.next();
            if (!hasOne) {
                return null;
            }   
            
            ArrayList<Tag> tags = new ArrayList<>();
            ArrayList<Course> courses = new ArrayList<>();
            
            int id = results.getInt("id");
            String author = results.getString("author");
            title = results.getString("title");
            String type = results.getString("type");
            String ISBN = results.getString("ISBN");
            String comment = results.getString("comment");
            
            book = new Book(id, author,
                    title, type,
                    ISBN, tags, courses, comment);

            statement.close();
            
            
                
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tag JOIN BookTag ON BookTag.book_id = Tag.id JOIN Book ON BookTag.book_id = ?");
            stmt.setInt(1, id);
            ResultSet tagResults = stmt.executeQuery();
                
            while (tagResults.next()) {
                int tagId = tagResults.getInt("id");
                String name = tagResults.getString("name");
                tags.add(new Tag(tagId, name));
            }
                
            stmt.close();
                
            
                
            PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM Course JOIN CourseBook ON CourseBook.course_id = Course.id JOIN Book ON CourseBook.book_id = ?");
            stmt2.setInt(1, id);
            ResultSet courseResults = stmt2.executeQuery();
                
            while (courseResults.next()) {
                int courseId = tagResults.getInt("id");
                String name = tagResults.getString("name");
                courses.add(new Course(courseId, name));
            }
                
            stmt2.close();
            
            book.setTags(tags);
            book.setCourses(courses);
            
            results.close();
            connection.close();
        }

        return book;
    }
    
    @Override
    public List<Book> findAll() throws SQLException {
        
        List<Book> books;
        try (Connection connection = database.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM Book");
            books = new ArrayList<>();
            while (results.next()) {
                int id = results.getInt("id");
                String author = results.getString("author");
                String title = results.getString("title");
                String type = results.getString("type");
                String ISBN = results.getString("ISBN");
                String comment = results.getString("comment");
                
                statement.close();
                
                ArrayList<Tag> tags = new ArrayList<>();
                
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tag JOIN LinkTag ON LinkTag.link_id = Tag.id JOIN Link ON LinkTag.link_id = ?");
                stmt.setInt(1, id);
                ResultSet tagResults = stmt.executeQuery();
                
                while (tagResults.next()) {
                    int tagId = tagResults.getInt("id");
                    String name = tagResults.getString("name");
                    tags.add(new Tag(tagId, name));
                }
                
                stmt.close();
                
                ArrayList<Course> courses = new ArrayList<>();
                
                PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM Course JOIN CourseLink ON CourseLink.course_id = Course.id JOIN Link ON CourseLink.link_id = ?");
                stmt2.setInt(1, id);
                ResultSet courseResults = stmt2.executeQuery();
                
                while (courseResults.next()) {
                    int courseId = tagResults.getInt("id");
                    String name = tagResults.getString("name");
                    courses.add(new Course(courseId, name));
                }
                
                stmt2.close();
                
                Book book = new Book(id, author, title, type, ISBN, tags, courses, comment);
                
                books.add(book);
            }
            connection.close();
        }
        
        return books;
    }
    
    @Override
    public boolean save(Book book) throws SQLException {
        
        if (!(this.findOne(book.getTitle()) == null)) {
            return false;
        }
        
        try (Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Book(author, title, type, ISBN, comment)"
                    + " VALUES ( ? , ? , ? , ? , ?)");
            statement.setString(1, book.getAuthor());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getType());
            statement.setString(4, book.getISBN());
            statement.setString(5, book.getComment());
            
            statement.executeUpdate();
            statement.close();
            
            for (Tag tag: book.getTags()) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO BookTag(book_id, tag_id) VALUES (?, ?)");
                stmt.setInt(1, book.getId());
                stmt.setInt(2, tag.getId());
            }
            
            for (Course course: book.getCourses()) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO CourseBook(book_id, course_id) VALUES (?, ?)");
                stmt.setInt(1, book.getId());
                stmt.setInt(2, course.getId());
            }
            
            
            connection.close();
        }
        return true;
    }
    
    @Override
    public void delete(String title) throws Exception {
        try (Connection connection = database.getConnection()){ 
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Book WHERE title = ?");
            
            statement.setString(1, title);
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
    }
    
    @Override
    public boolean edit(Book book) throws SQLException {
        
        if (this.findOne(book.getTitle()) != null) {
            return false;
        }
        
        try (Connection connection = database.getConnection()) {
            
            connection.close();
        }
        return true;
        
    }
    
}
