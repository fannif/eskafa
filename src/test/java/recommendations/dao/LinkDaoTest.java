
package recommendations.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import recommendations.domain.Course;
import recommendations.domain.Link;
import recommendations.domain.Tag;


public class LinkDaoTest {
    Database testDatabase;
    LinkDao linkDao;
    
    @Before
    public void setUp() throws ClassNotFoundException {
        testDatabase = new Database("jdbc:sqlite:recommendationsTest.db");
        linkDao = new LinkDao(testDatabase);
    }
    
    @Test
    public void LinkCanBeCreated() throws SQLException {
        ArrayList<Tag> tags = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course (1,"Ohjelmistotuotanto"));        
        Link link = new Link(1, "Ohtu", "https://ohjelmistotuotanto-hy.github.io/", "Link", "", tags, courses, "");
       
        assertTrue(linkDao.save(link));
        assertEquals(link.getTitle(), linkDao.findOne("Ohtu").getTitle());
    }
    
    @Test 
    public void listAllListsAllLinks() throws SQLException {
        ArrayList<Tag> tags = new ArrayList<>();
        ArrayList<Tag> tags2 = new ArrayList<>();
        tags.add(new Tag(1,"news"));
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Course> courses2 = new ArrayList<>();
        courses2.add(new Course (1,"Ohjelmistotuotanto"));
        linkDao.save(new Link(0, "Kaleva", "https://www.kaleva.fi", "Link", "", tags, courses, "news"));
        linkDao.save(new Link(0, "Ohtu", "https://ohjelmistotuotanto-hy.github.io/", "Link", "", tags2, courses2, ""));
        
        assertEquals(2, linkDao.findAll().size());
        assertEquals("Kaleva", linkDao.findAll().get(0).getTitle());
    }
    
    @After
    public void tearDown() throws SQLException {
        String sql = "DROP TABLE Link";
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
        sql = "DROP TABLE LinkTag";
        connection = testDatabase.getConnection(); 
        stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
        connection.close();
        sql = "DROP TABLE CourseLink";
        connection = testDatabase.getConnection(); 
        stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
        connection.close();          
    }
}
