
package recommendations.dao;

import java.sql.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import recommendations.domain.*;
import static org.hamcrest.CoreMatchers.is;


public class BookDaoTest {
    Database testDatabase;
    BookDao bookDao;
    Book book1;
    Book book2;
    Book book3;
            
    
    @Before
    public void setUp() throws ClassNotFoundException {
        testDatabase = new Database("jdbc:sqlite:recommendationsTest.db");
        bookDao = new BookDao(testDatabase);
        ArrayList<Tag> tags1 = new ArrayList<>();
        ArrayList<Tag> tags2 = new ArrayList<>();
        tags1.add(new Tag(1,"clean code"));
        tags2.add(new Tag(2,"Security"));
        tags2.add(new Tag(3,"Popular"));
        ArrayList<Course> courses1 = new ArrayList<>();
        ArrayList<Course> courses2 = new ArrayList<>();
        courses1.add(new Course(1,"Ohjelmistotuotanto"));
        courses1.add(new Course(2,"OhJa"));
        courses2.add(new Course(3,"Cypersecurity"));
        book1 = new Book(1, "Robert C. Martin", "Clean Code", "Book", "978-0-13-235088-4", tags1, courses1, "Must have!");
        book2 = new Book(2, "Bruce Schneier", "Beyond Fear", "Book", "0-387-02620-79781119092438", tags2, courses2, "");
        book3 = new Book(3, "Bruce Schneier", "Secrets & Lies", "Book", "0-387-02620-7", tags2, courses2, "");
    }
    
    @Test
    public void bookCanBeCreated() throws SQLException {
        assertTrue(bookDao.save(book1));
        assertEquals(book1, bookDao.findOne("Clean Code"));
    }
    
    @Test 
    public void listAllListsAllBooks() throws SQLException {
        bookDao.save(book1);
        bookDao.save(book2);
        bookDao.save(book3);
        
        assertEquals(3, bookDao.findAll().size());
        assertEquals("Beyond Fear", bookDao.findAll().get(1).getTitle());
    }
    @Test
    public void findeOneReturnsRightBookInRightForm() throws SQLException {
        bookDao.save(book3);
        assertEquals("\tType: Book\n\tTitle: Secrets & Lies\n\tAuthor: Bruce Schneier\n\tISBN: 0-387-02620-7\n\tTags:"
                + "|Security|Popular|\n\tRelated courses:|Cypersecurity|\n\t\n" ,bookDao.findOne("Secrets & Lies").toString());
    }
    
    @Test
    public void findByTagReturnsCorrectResult() throws SQLException {
        bookDao.save(book1);
        bookDao.save(book2);
        bookDao.save(book3);
        
        ArrayList<Book> expected = new ArrayList<>();
        expected.add(book2);
        expected.add(book3);
        
        assertThat(bookDao.findByTag("Security"), is(expected));
    }
    
    @Test
    public void findByTagReturnsCorrectResultWhenNoMatches() throws SQLException {
        bookDao.save(book1);
        
        assertEquals(0, bookDao.findByTag("coding").size());
    }
    
    @Test
    public void bookCanBeRemovedWithCorrectTitle() throws SQLException, Exception{
        bookDao.save(book1);
        bookDao.save(book2);
        bookDao.save(book3);
        
        bookDao.delete("Clean Code");
        assertEquals(null, bookDao.findOne("Clean Code"));
    }
    @Test
    public void noBookIsRemovedWithNonExistingTitle() throws SQLException, Exception {
        bookDao.save(book1);
        bookDao.save(book2);
        bookDao.save(book3);
        List<Book> expected = new ArrayList<>();
        expected.add(book1);
        expected.add(book2);
        expected.add(book3);
        bookDao.delete("Clean Horse");
        assertThat(bookDao.findAll(), is(expected));        
    }
    
    @Test
    public void bookCanBeEditet() throws SQLException {
        bookDao.save(book1);
        ArrayList<Tag> tags1 = new ArrayList<>();
        tags1.add(new Tag(1,"refactor"));
        ArrayList<Course> courses1 = new ArrayList<>();
        courses1.add(new Course(1,"Ohjelmistotuotanto"));
        courses1.add(new Course(2,"OhJa"));
        Book updatet = new Book(1, "Robert C. Martin", "Clean Code", "Book", "978-0-13-235088-4", tags1, courses1, "Must have!");
        
        
        bookDao.edit(updatet);
        assertEquals("Clean Code", bookDao.findByTag("refactor").get(0).getTitle());
    }
    
    @Test
    public void bookCanBeFoundByACourseName() throws SQLException {
        bookDao.save(book2);
        assertEquals("Beyond Fear", bookDao.findByWord("Cypersecurity").get(0).getTitle());
    }
    
    @Test
    public void noBookCanBeFoundByNonexistingCourseName() throws SQLException {
        bookDao.save(book2);
        assertEquals(0, bookDao.findByWord("not there").size());
    }
    
    @Test
    public void bookCanBeFoundByAnAuthor() throws SQLException {
        bookDao.save(book1);
        assertEquals("Robert C. Martin", bookDao.findByWord("Robert C. Martin").get(0).getAuthor());
    }
    
    @After
    public void tearDown() throws SQLException {
        Connection connection = testDatabase.getConnection(); 
        Statement stmt = connection.createStatement();
        stmt.execute("DROP TABLE Book");
        stmt = connection.createStatement();
        stmt.execute("DROP TABLE Tag");
        stmt = connection.createStatement();
        stmt.execute("DROP TABLE BookTag");
        stmt = connection.createStatement();
        stmt.execute("DROP TABLE CourseBook");
        stmt = connection.createStatement();
        stmt.execute("DROP TABLE Course");
        stmt.close();
        connection.close();        
    }
}
