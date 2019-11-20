
package recommendations.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import recommendations.domain.Book;

public class fileDao implements readerDao<Book, String> {
    
    Scanner fileReader;
    File file;
    
    public fileDao() {
        file = new File("books.csv");
        connectReader();
    }
    
    @Override
    public Book findOne(String title) {
        connectReader();
        while(fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            if (line.split(",")[1].equals(title)) {
                fileReader.close();
                return convert(line);
            }
        }
        fileReader.close();
        return null;
    }
    
    @Override
    public ArrayList<Book> findAll() {
        connectReader();
        ArrayList<Book> tips = new ArrayList<>();
        while(fileReader.hasNextLine()) {
            tips.add(convert(fileReader.nextLine()));
        }
        fileReader.close();
        return tips;
    }
    
    @Override
    public boolean save(Book tip) throws IOException {
        String newLine = "";
        newLine = newLine + tip.getAuthor() + ",";
        newLine = newLine + tip.getTitle() + ",";
        newLine = newLine + tip.getType()+ ",";
        newLine = newLine + tip.getISBN() + ",";
        
        for (String tag: tip.getTag()) {
            newLine = newLine + tag + ";";
        }
        
        for (String related: tip.getRelatedCourses()) {
            newLine = newLine + related + ";";
        }
        
        newLine = newLine + tip.getComment();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.csv", true))) {
            writer.append("\n");
            writer.append(newLine);
        }
        
        return true;
    }
    
    // Deletes every book by given title
    @Override
    public void delete(String title) throws Exception {
        File temp = new File("temp.csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(temp))) {
            connectReader();
            
            String line = "";
            
            while(fileReader.hasNextLine()) {
                if (line.split(",")[1].equals(title)) {
                    continue;
                }
                writer.write(line + "\n");
            }
        }
        fileReader.close();
        
        file.delete();
        temp.renameTo(new File("books.csv"));
    }
    
    // Converts line from file into book
    private Book convert(String line) {
        String[] parts = line.split(",");
        String[] tags = parts[4].split(";");
        String[] related = parts[5].split(";");
        ArrayList<String> bookTags = new ArrayList<>();
        ArrayList<String> bookRelated = new ArrayList<>();
        
        for (String tag: tags) {
            bookTags.add(tag);
        }
        
        for (String relation: related) {
            bookRelated.add(relation);
        }
        
        return new Book(parts[0], parts[1], parts[2], parts[3], bookTags, bookRelated, parts[6]);
    }
    
    private void connectReader() {
        try (Scanner fReader = new Scanner(new File("books.csv"))) {
            this.fileReader = fReader;
        } catch (Exception e) {
            System.out.println("No file 'books.csv' found in root directory.");
        }
    }
    
}
