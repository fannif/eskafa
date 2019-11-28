
package recommendations.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import recommendations.domain.Book;
import recommendations.domain.Course;
import recommendations.domain.Tag;


public class BookDaoTest {
    Database testDatabase;
    BookDao bookDao;
    
    @Before
    public void setUp() throws ClassNotFoundException {
        testDatabase = new Database("jdbc:sqlite:recommendationsTest.db");
        bookDao = new BookDao(testDatabase);
    }
    
    @Test
    public void bookCanBeCreated() throws SQLException {
        ArrayList<Tag> tags1 = new ArrayList<>();
        tags1.add(new Tag(1,"clean code"));
        ArrayList<Course> courses1 = new ArrayList<>();
        courses1.add(new Course(1,"Ohjelmistotuotanto"));
        
        Book book = new Book(1, "Robert C. Martin", "Clean Code", "Book", "978-0-13-235088-4", tags1, courses1, "Must have!");
        assertTrue(bookDao.save(book));
        assertEquals(book, bookDao.findOne("Clean Code"));
    }
    
    @Test 
    public void listAllListsAllBooks() throws SQLException {
        ArrayList<Tag> tags1 = new ArrayList<>();
        ArrayList<Tag> tags2 = new ArrayList<>();
        tags1.add(new Tag(1,"clean code"));
        tags2.add(new Tag(2,"Security"));
        tags2.add(new Tag(3,"Popular"));
        ArrayList<Course> courses1 = new ArrayList<>();
        ArrayList<Course> courses2 = new ArrayList<>();
        courses1.add(new Course(1,"Ohjelmistotuotanto"));
        courses1.add(new Course(2,"OhJa"));
        bookDao.save(new Book(1, "Robert C. Martin", "Clean Code", "Book", "978-0-13-235088-4", tags1, courses1, "Must have!"));
        bookDao.save(new Book(2, "Bruce Schneier", "Beyond Fear", "Book", "0-387-02620-79781119092438", tags2, courses2, ""));
        bookDao.save(new Book(3, "Bruce Schneier", "Secrets & Lies", "Book", "0-387-02620-7", tags2, courses2, ""));
        
        assertEquals(3, bookDao.findAll().size());
        assertEquals("Beyond Fear", bookDao.findAll().get(1).getTitle());
    }
    @Test
    public void findeOneReturnsRightBookInRightFormWithEmptyCoursesField() throws SQLException {
        ArrayList<Tag> tags2 = new ArrayList<>();
        tags2.add(new Tag(0,"Security"));
        ArrayList<Course> courses2 = new ArrayList<>(); 
        bookDao.save(new Book(0, "Bruce Schneier", "Secrets & Lies", "Book", "0-387-02620-7", tags2, courses2, ""));
        assertEquals("Type: Book\n\tTitle: Secrets & Lies\n\tAuthor: Bruce Schneier\n\tISBN: 0-387-02620-7\n\tTags:"
                + "|Security|\n\tRelated courses:\n\t\n" ,bookDao.findOne("Secrets & Lies").toString());
    }
    @After
    public void tearDown() throws SQLException {
        String sql = "DROP TABLE Book";
        Connection connection = testDatabase.getConnection(); 
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
        connection.close();
        sql = "DROP TABLE Tag";
        connection = testDatabase.getConnection(); 
        stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
        connection.close();        
        sql = "DROP TABLE BookTag";
        connection = testDatabase.getConnection(); 
        stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
        connection.close();
        sql = "DROP TABLE CourseBook";
        connection = testDatabase.getConnection(); 
        stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
        connection.close();        
        
    }
}
