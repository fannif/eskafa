
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
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(address);
    }
    
    public void createTableBook() throws SQLException {
        try (Connection connection = this.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(""
                    + "CREATE TABLE IF NOT EXISTS Book"
                    + " (id SERIAL,"
                    + " author VARCHAR(72),"
                    + " title VARCHAR(72),"
                    + " type VARCHAR(72),"
                    + " ISBN VARCHAR(72),"
                    + " tag_id INTEGER, "
                    + " course_id INTEGER"
                    + " comment VARCHAR(255))");
            stmt.executeUpdate();
            stmt.close();
        }
    }
    
    public void createTableLink() throws SQLException {
        try (Connection connection = this.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(""
                    + "CREATE TABLE IF NOT EXISTS Link"
                    + " (id SERIAL,"
                    + " title VARCHAR(72),"
                    + " URL VARCHAR(255),"
                    + " type VARCHAR(72),"
                    + " metadata VARCHAR(255),"
                    + " tag_id INTEGER, "
                    + " course_id INTEGER"
                    + " comment VARCHAR(255))");
            stmt.executeUpdate();
            stmt.close();
        }
    }
    
}
