
package recommendations.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    @Override
    public ArrayList<Book> findByTag(String tag) {
        ArrayList<Book> books = new ArrayList<>();
        
        try {
            for (Book book: this.findAll()) {
                for (Tag bookTag: book.getTags()) {
                    if (bookTag.getName().toLowerCase().equals(tag.toLowerCase())) {
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
    public ArrayList<Book> findByCourse(String course) {
        ArrayList<Book> books = new ArrayList<>();
        
        try {
            for (Book book: this.findAll()) {
                for (Course bookCourse: book.getCourses()) {
                    if (bookCourse.getName().toLowerCase().equals(course.toLowerCase())) {
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

            
                
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tag JOIN BookTag ON BookTag.Tag_id = Tag.id JOIN Book ON BookTag.book_id = Book.id WHERE Book.id = ?");
            stmt.setInt(1, id);
            ResultSet tagResults = stmt.executeQuery();
                
            while (tagResults.next()) {
                int tagId = tagResults.getInt("id");
                String name = tagResults.getString("name");
                tags.add(new Tag(tagId, name));
            }
                
            stmt.close();
                
            
                
            PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM Course JOIN CourseBook ON CourseBook.course_id = Course.id JOIN Book ON CourseBook.book_id = Book.id WHERE Book.id = ?");
            stmt2.setInt(1, id);
            ResultSet courseResults = stmt2.executeQuery();
                
            while (courseResults.next()) {
                int courseId = courseResults.getInt("id");
                String name = courseResults.getString("name");
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
        
        List<Book> books =new ArrayList<>();
        try (Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Book");
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int id = results.getInt("id");
                String author = results.getString("author");
                String title = results.getString("title");
                String type = results.getString("type");
                String ISBN = results.getString("ISBN");
                String comment = results.getString("comment");
                
                
                ArrayList<Tag> tags = new ArrayList<>();
                
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tag JOIN BookTag ON BookTag.tag_id = Tag.id JOIN Book ON BookTag.Book_id = Book.id WHERE Book.id = ?");
                stmt.setInt(1, id);
                ResultSet tagResults = stmt.executeQuery();
                
                while (tagResults.next()) {
                    int tagId = tagResults.getInt("id");
                    String name = tagResults.getString("name");
                    tags.add(new Tag(tagId, name));
                }
                
                stmt.close();
                
                ArrayList<Course> courses = new ArrayList<>();
                
                PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM Course JOIN CourseBook ON CourseBook.course_id = Course.id JOIN Book ON CourseBook.book_id = Book.id WHERE Book.id = ?");
                stmt2.setInt(1, id);
                ResultSet courseResults = stmt2.executeQuery();
                
                while (courseResults.next()) {
                    int courseId = courseResults.getInt("id");
                    String name = courseResults.getString("name");
                    courses.add(new Course(courseId, name));
                }
                
                stmt2.close();
                
                Book book = new Book(id, author, title, type, ISBN, tags, courses, comment);
                
                books.add(book);
            }
            statement.close();
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
            
            BookTagDao bookTagDao = new BookTagDao(database);
            TagDao tagDao = new TagDao(database);
            
            int bookId = this.findOne(book.getTitle()).getId();
            
            for (Tag tag: book.getTags()) {
                
                
                int tagId = 0;
                if (tagDao.findOne(tag.getName()) != null) {
                    tagId = tagDao.findOne(tag.getName()).getId();
                } else {
                    tagDao.save(tag);
                    tagId = tagDao.findOne(tag.getName()).getId();
                }
                
                bookTagDao.save(bookId, tagId);
            }
            
            CourseBookDao courseBookDao = new CourseBookDao(database);
            CourseDao courseDao = new CourseDao(database);
            
            for (Course course: book.getCourses()) {
                
                int courseId = 0;
                if (courseDao.findOne(course.getName()) != null) {
                    courseId = courseDao.findOne(course.getName()).getId();
                } else {
                    courseDao.save(course);
                    courseId = courseDao.findOne(course.getName()).getId();
                }
                
                courseBookDao.save(bookId, courseId);
            }
            
            
            connection.close();
        }
        return true;
    }
    
    @Override
    public void delete(String title) throws Exception {
        try (Connection connection = database.getConnection()) { 
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Book WHERE title = ?");
            
            statement.setString(1, title);
            statement.executeUpdate();
            connection.close();
        }
    }
    
    @Override
    public boolean edit(Book book) throws SQLException {
        
        if ((this.findOne(book.getTitle()) != null) && this.findOne(book.getTitle()).getId() != book.getId()) {
            return false;
        }
        
        try (Connection connection = database.getConnection()) {
            
           PreparedStatement statement = connection.prepareStatement("UPDATE Book SET title = ?, author = ?, comment = ? WHERE id = ?");
            
           statement.setString(1, book.getTitle());
           statement.setString(2, book.getAuthor());
           statement.setString(3, book.getComment());
           statement.setInt(4, book.getId());
           statement.executeUpdate();
           
            BookTagDao bookTagDao = new BookTagDao(database);
            TagDao tagDao = new TagDao(database);
            
            try {
                bookTagDao.deleteByBook(book.getId());
            } catch (Exception ex) {
                Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for (Tag tag: book.getTags()) {
                
                
                int tagId = 0;
                if (tagDao.findOne(tag.getName()) != null) {
                    tagId = tagDao.findOne(tag.getName()).getId();
                } else {
                    tagDao.save(tag);
                    tagId = tagDao.findOne(tag.getName()).getId();
                }
                
                

                int bookId = this.findOne(book.getTitle()).getId();
                
                bookTagDao.save(bookId, tagId);
            }
            
            CourseBookDao courseBookDao = new CourseBookDao(database);
            CourseDao courseDao = new CourseDao(database);
            
            try {
                courseBookDao.deleteByBook(book.getId());
            } catch (Exception ex) {
                Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for (Course course: book.getCourses()) {
                
                int courseId = 0;
                if (courseDao.findOne(course.getName()) != null) {
                    courseId = courseDao.findOne(course.getName()).getId();
                } else {
                    courseDao.save(course);
                    courseId = courseDao.findOne(course.getName()).getId();
                }
                
                int bookId = this.findOne(book.getTitle()).getId();
                
                courseBookDao.save(bookId, courseId);
            }
           
           connection.close();
        }
        return true;
        
    }
    
    @Override
    public List<Book> findByWord(String word) throws SQLException {
        
        List<Book> books = new ArrayList<>();
        
        try (Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * From Book WHERE"
                    + " (author LIKE ?) OR (title LIKE ?) "
                    + "OR (comment LIKE ?)");
            statement.setString(1, "%"+word+"%");
            statement.setString(2, "%"+word+"%");
            statement.setString(3, "%"+word+"%");
            
            ResultSet results = statement.executeQuery();
            
            while(results.next()) {
            
                int id = results.getInt("id");
                String author = results.getString("author");
                String title = results.getString("title");
                String type = results.getString("type");
                String ISBN = results.getString("ISBN");
                String comment = results.getString("comment");
            
                ArrayList<Tag> tags = new ArrayList<>();
                
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tag JOIN BookTag ON BookTag.tag_id = Tag.id JOIN Book ON BookTag.Book_id = Book.id WHERE Book.id = ?");
                stmt.setInt(1, id);
                ResultSet tagResults = stmt.executeQuery();
                
                while (tagResults.next()) {
                    int tagId = tagResults.getInt("id");
                    String name = tagResults.getString("name");
                    tags.add(new Tag(tagId, name));
                }
                
                stmt.close();
                
                ArrayList<Course> courses = new ArrayList<>();
                
                PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM Course JOIN CourseBook ON CourseBook.course_id = Course.id JOIN Book ON CourseBook.book_id = Book.id WHERE Book.id = ?");
                stmt2.setInt(1, id);
                ResultSet courseResults = stmt2.executeQuery();
                
                while (courseResults.next()) {
                    int courseId = courseResults.getInt("id");
                    String name = courseResults.getString("name");
                    courses.add(new Course(courseId, name));
                }
                
                stmt2.close();
                
                Book book = new Book(id, author,
                    title, type,
                    ISBN, tags, courses, comment);
            
                books.add(book);
            
            }
            
            
            for (Book b: this.findByTag(word)) {
                if (!books.isEmpty() && !books.contains(b)) {
                    books.add(b);
                }
            }
            
            for (Book b: this.findByCourse(word)) {
                if (!books.isEmpty() && !books.contains(b)) {
                    books.add(b);
                }
            }
            
            
            statement.close();
            connection.close();
            
        } 
        
        
        return books;
    }
    
}
