
package recommendations.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import recommendations.domain.Tag;


public class TagDaoTest {
    Database testDatabase;
    TagDao tagDao;
    
    @Before
    public void setUp() throws ClassNotFoundException {
        testDatabase = new Database("jdbc:sqlite:recommendationsTest.db");
        tagDao = new TagDao(testDatabase);
    }

    
    @Test 
    public void listAllListsAllTags() throws SQLException {
        tagDao.save(new Tag(0,"Jee"));
        tagDao.save(new Tag(0,"Joo"));
        tagDao.save(new Tag(0,"Juu"));        
        assertEquals(3, tagDao.findAll().size());
        assertEquals("Jee", tagDao.findAll().get(0).getName());
    }
    
    @After
    public void tearDown() throws SQLException {
        String sql = "DROP TABLE Tag";
        Connection connection = testDatabase.getConnection(); 
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
        connection.close();                  
    }
}    