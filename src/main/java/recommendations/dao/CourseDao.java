
package recommendations.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import recommendations.domain.Course;


public class CourseDao implements ReaderDao<Course, String> {

    private Database database;
    
    public CourseDao(Database database) {
        this.database = database;
    }

    @Override
    public List<Course> findByTag(String title) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Course findOne(String title) throws SQLException {
        Course course = null;    
        try (Connection connection = database.getConnection()) {
            String sql = "SELECT * FROM Course WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            ResultSet results = statement.executeQuery();
            boolean hasOne = results.next();
            if (!hasOne) {
                return null;
            }
            int id = results.getInt("id");
            String name = results.getString("name");
            
            course = new Course(id, name);

            statement.close();
        }
        
        return course;
    }

    @Override
    public List<Course> findAll() throws SQLException {
        List<Course> courses;
        try (Connection connection = database.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM Course");
            courses = new ArrayList<>();
            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");

                Course course = new Course(id, name);
                courses.add(course);
            }
            connection.close();
        }

        return courses;
    }
    

    @Override
    public boolean save(Course course) throws SQLException {
                
        if (!(this.findOne(course.getName()) == null)) {
            return false;
        }
        
        try (Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Course (name)"
                    + " VALUES ( ? )");
            statement.setString(1, course.getName());
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
        return true;
    }

    @Override
    public void delete(String title) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean edit(Course tip) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
