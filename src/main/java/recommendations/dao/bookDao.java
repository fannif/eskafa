
package recommendations.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    public List<Book> findAll() throws SQLException {
        
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM Book");
        
        List<Book> books = new ArrayList<>();
        
        while (results.next()) {
            int id = results.getInt("id");
            String author = results.getString("author");
            String title = results.getString("title");
            String type = results.getString("type");
            String ISBN = results.getString("ISBN");
            String comment = results.getString("comment");
            
            // Lisää tägien ja kurssien listaus!
            
            Book book = new Book(id, author, title, type, ISBN, new ArrayList<String>(), new ArrayList<String>(), comment);
        }
        
        connection.close();
        
        return books;
    }
    
    @Override
    public void save(Book book) throws SQLException {
        
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Book(author, title, type, ISBN, comment)"
                + " VALUES ( ? , ? , ? , ? , ?)");
        
        statement.setString(1, book.getAuthor());
        statement.setString(2, book.getTitle());
        statement.setString(3, book.getType());
        statement.setString(4, book.getISBN());
        statement.setString(5, book.getComment());
        
        connection.close();
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
