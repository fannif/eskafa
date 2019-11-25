
package recommendations.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import recommendations.domain.Book;

public class bookDao implements readerDao<Book, String> {
    
    private Database database;
    
    public bookDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Book findOne(String title) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Book WHERE title = ?");
        statement.setString(1, title);

        ResultSet results = statement.executeQuery();
        boolean hasOne = results.next();
        if (!hasOne) {
            return null;
        }

        Book book = new Book(results.getInt("id"), results.getString("author"),
            results.getString("title"), results.getString("type"),
            results.getString("ISBN"), new ArrayList<String>(), new ArrayList<String>(), results.getString("comment"));
        
        // Lisää ArrayListeihin vielä tägit ja kurssit!
        
        
        statement.close();
        results.close();

        connection.close();

        return book;
    }
    
    @Override
    public ArrayList<Book> findAll() throws SQLException {
        
        return null;
    }
    
    @Override
    public boolean save(Book tip) throws IOException {
        
        
        return true;
    }
    
    @Override
    public void delete(String title) throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Book WHERE title = ?");
        
        statement.setString(1, title);
        statement.executeUpdate();
        
        statement.close();
        connection.close();
    }
    
    // Converts line from file into book
    private Book convert(String line) {
        
        return null;
    }
    
    private void connectReader() throws Exception {
         
        
    }
    
}
