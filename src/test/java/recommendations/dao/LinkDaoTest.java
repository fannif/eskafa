
package recommendations.dao;

import java.sql.*;
import java.util.*;
import static org.hamcrest.CoreMatchers.is;
import org.junit.*;
import static org.junit.Assert.*;
import recommendations.domain.*;

public class LinkDaoTest {
    Database testDatabase;
    LinkDao linkDao;
    Link link1;
    Link link2;
    
    @Before
    public void setUp() throws ClassNotFoundException {
        testDatabase = new Database("jdbc:sqlite:recommendationsTest.db");
        linkDao = new LinkDao(testDatabase);
        ArrayList<Tag> tags = new ArrayList<>();
        ArrayList<Tag> tags2 = new ArrayList<>();
        tags.add(new Tag(1,"news"));
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Course> courses2 = new ArrayList<>();
        courses2.add(new Course (1,"Ohjelmistotuotanto"));
        Link link1 = new Link(0, "Kaleva", "https://www.kaleva.fi", "Link", "", tags, courses, "news");
        Link link2 = new Link(0, "Ohtu", "https://ohjelmistotuotanto-hy.github.io/", "Link", "", tags2, courses2, "");
    }
    
    @Test
    public void LinkCanBeCreated() throws SQLException {
        assertTrue(linkDao.save(link2));
        assertEquals(link2.getTitle(), linkDao.findOne("Ohtu").getTitle());
    }
    
    @Test 
    public void listAllListsAllLinks() throws SQLException {
        linkDao.save(link1);
        linkDao.save(link2);
        
        assertEquals(2, linkDao.findAll().size());
        assertEquals("Kaleva", linkDao.findAll().get(0).getTitle());
    }
    
    @Test
    public void findByTagReturnsCorrectResult() throws SQLException {
        linkDao.save(link1);
        linkDao.save(link2);
        
        assertEquals(1, linkDao.findByTag("news").size());
        assertEquals("Kaleva", linkDao.findByTag("news").get(0).getTitle());
    }
    
    @Test
    public void findByTagReturnsCorrectResultWhenNoMatches() throws SQLException {
        linkDao.save(link1);
        
        assertEquals(0, linkDao.findByTag("coding").size());
    }
 
    
    @Test
    public void bookCanBeRemovedWithCorrectName() throws SQLException, Exception{
        linkDao.save(link1);
        linkDao.save(link2);
        
        linkDao.delete("Ohtu");
        assertEquals(null, linkDao.findOne("Ohtu"));
    }
    @Test
    public void noBookIsRemovedWithNonExistingTitle() throws SQLException, Exception {
        linkDao.save(link1);
        linkDao.save(link2);
        List<Link> expected = new ArrayList<>();
        expected.add(link1);
        expected.add(link2);

        linkDao.delete("Horse Ohtu");
        assertThat(linkDao.findAll(), is(expected));        
    }
    
    @After
    public void tearDown() throws SQLException {
        Connection connection = testDatabase.getConnection(); 
        Statement stmt = connection.createStatement();
        stmt.execute("DROP TABLE Link");
        stmt = connection.createStatement();
        stmt.execute("DROP TABLE Tag");
        stmt = connection.createStatement();
        stmt.execute("DROP TABLE LinkTag");
        stmt = connection.createStatement();
        stmt.execute("DROP TABLE CourseLink");
        stmt = connection.createStatement();
        stmt.execute("DROP TABLE Course");
        stmt.close();
        connection.close();          
    }
}
