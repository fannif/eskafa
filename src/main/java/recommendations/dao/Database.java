
package recommendations.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    
    private String address;
    
    public Database(String address) throws ClassNotFoundException {
        this.address = address;
        try {
            createTableLink();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            createTableBook();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            createTableBookTag();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            createTableLinkTag();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            createTableCourseBook();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            createTableCourseLink();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            createTableTag();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            createTableCourse();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(address);
    }
    
    public void createTableBook() throws SQLException {
        try (Connection connection = this.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(""
                    + "CREATE TABLE IF NOT EXISTS Book"
                    + " (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + " author VARCHAR(72),"
                    + " title VARCHAR(72),"
                    + " type VARCHAR(72),"
                    + " ISBN VARCHAR(72),"
                    + " comment VARCHAR(255))");
            stmt.executeUpdate();
            stmt.close();
            
            connection.close();
        }
    }
    
    public void createTableLink() throws SQLException {
        try (Connection connection = this.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(""
                    + "CREATE TABLE IF NOT EXISTS Link"
                    + " (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + " title VARCHAR(72),"
                    + " URL VARCHAR(255),"
                    + " type VARCHAR(72),"
                    + " metadata VARCHAR(8000),"
                    + " comment VARCHAR(255))");
            stmt.executeUpdate();
            stmt.close();
            
            connection.close();
        }
    }

    public void createTableTag() throws SQLException {
        try (Connection connection = this.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(""
                    + "CREATE TABLE IF NOT EXISTS Tag"
                    + " (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + " name VARCHAR(72))");
            stmt.executeUpdate();
            stmt.close();
            
            connection.close();
        }
    }

    public void createTableCourse() throws SQLException {
        try (Connection connection = this.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(""
                    + "CREATE TABLE IF NOT EXISTS Course"
                    + " (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + " name VARCHAR(72))");
            stmt.executeUpdate();
            stmt.close();
            
            connection.close();
        }
    }

    public void createTableLinkTag() throws SQLException {
        try (Connection connection = this.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(""
                    + "CREATE TABLE IF NOT EXISTS LinkTag"
                    + " (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + " link_id INTEGER,"
                    + " tag_id INTEGER,"
                    + " FOREIGN KEY (link_id) REFERENCES Link(id),"
                    + " FOREIGN KEY (tag_id) REFERENCES Tag(id))");
            stmt.executeUpdate();
            stmt.close();
            
            connection.close();
        }
    }

    public void createTableBookTag() throws SQLException {
        try (Connection connection = this.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(""
                    + "CREATE TABLE IF NOT EXISTS BookTag"
                    + " (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + " book_id INTEGER,"
                    + " tag_id INTEGER,"
                    + "FOREIGN KEY (book_id) REFERENCES Book(id), "
                    + "FOREIGN KEY (tag_id) REFERENCES Tag(id))");
            stmt.executeUpdate();
            stmt.close();
            
            connection.close();
        }
    }

    public void createTableCourseBook() throws SQLException {
        try (Connection connection = this.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(""
                    + "CREATE TABLE IF NOT EXISTS CourseBook"
                    + " (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + " course_id INTEGER,"
                    + " book_id INTEGER,"
                    + " FOREIGN KEY (book_id) REFERENCES Book(id),"
                    + " FOREIGN KEY (course_id) REFERENCES Course(id))");
            stmt.executeUpdate();
            stmt.close();
            
            connection.close();
        }
    }

    public void createTableCourseLink() throws SQLException {
        try (Connection connection = this.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(""
                    + "CREATE TABLE IF NOT EXISTS CourseLink"
                    + " (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + " course_id INTEGER,"
                    + " link_id INTEGER,"
                    + " FOREIGN KEY (link_id) REFERENCES Link(id),"
                    + " FOREIGN KEY (course_id) REFERENCES Course(id))");
            stmt.executeUpdate();
            stmt.close();
            
            connection.close();
        }
    }
    
}
